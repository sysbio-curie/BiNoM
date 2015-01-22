
library(RJSONIO)
library(RCurl)

NaviCell <- setClass(
    # name for the class
    "NaviCell",

    # Define the slots
    slots = c( 
        #browser_command = "character", 
        proxy_url = "character",
        map_url = "character",
        msg_id = "numeric",
        session_id = "character",
        hugo_list = "vector"
        #hugo_map = "list",
    ),

    # Set the default values for the slots. 
    prototype = list( 
        #browser_command = "", 
        proxy_url = "https://navicell.curie.fr/cgi-bin/nv_proxy.php",
        map_url = "https://navicell.curie.fr/navicell/maps/cellcycle/master/index.php",
        #proxy_url = "https://acsn.curie.fr/nv2.2/cgi-bin/nv_proxy.php",
        #map_url = "https://acsn.curie.fr/nv2.2/navicell/maps/cellcycle_light/master/index.php",
        msg_id = 1000,
        session_id = "",
        hugo_list = vector()
        #hugo_map = list(),
    ),

)

setGeneric("nvIncMessageId", function(object) standardGeneric("nvIncMessageId"))
setMethod("nvIncMessageId",
    signature(object = "NaviCell"),
    function(object) {
        object@msg_id <- object@msg_id + 1
        return(object)
    }
)

setGeneric("nvGenSessionId", function(object) standardGeneric("nvGenSessionId"))
setMethod("nvGenSessionId",
    signature(object = "NaviCell"),
    function(object) {
        object <- nvIncMessageId(object)
        response = postForm(object@proxy_url, style = 'POST', id = "1", perform = "genid", msg_id = object@msg_id, mode = "session", .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        if (response != "") {
            object@session_id <- response
        }
        return(object)
    }
)

setGeneric("nvSetZoom", function(object, ...) standardGeneric("nvSetZoom"))
setMethod("nvSetZoom",
    signature(object = "NaviCell"),
    function(object, mod, zoom_level) {
        object <- nvIncMessageId(object)
        list_param <- list(module=mod, args = array(zoom_level), msg_id = object@msg_id, action = 'nv_set_zoom')  
        str_data <- nv_make_data(nv_format_json(list_param))
        #print(data)
        response <- postForm(object@proxy_url, style='POST', id=object@session_id, mode='cli2srv', perform='send_and_rcv', data=str_data, .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        message(response)
        return(object)
    }
)

#' launch the (default) client browser
#' 
#' @param nv NaviCell object
#' @export
setGeneric("nvLaunchBrowser", function(object) standardGeneric("nvLaunchBrowser"))
setMethod("nvLaunchBrowser",
signature(object = "NaviCell"),
    function(object) {
        object <- nvIncMessageId(object)
        if (object@session_id == "") {
            object <- nvGenSessionId(object)
        }
        url <- paste(object@map_url, '?id=', object@session_id, sep = '')
        browseURL(url)
        return(object)
    }
)

setGeneric("nvListSessions", function(object) standardGeneric("nvListSessions"))
setMethod("nvListSessions",
signature(object = "NaviCell"),
    function(object) {
        response <- postForm(object@proxy_url, style='POST', id="1", perform="list", msg_id=object@msg_id, mode="session", .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        message(response)
        return(object)
    }
)


setGeneric("nvAttachSession", function(object, ...) standardGeneric("nvAttachSession"))
setMethod("nvAttachSession",
signature(object = "NaviCell"),
    function(object, new_session_id) {
        if (object@session_id != "") {
            warning("Session id already set.")
            return(object)
        }
        object <- nvIncMessageId(object)
        # check session id on NaviCell server
        response <- postForm(object@proxy_url, style='POST', id = new_session_id, msg_id = object@msg_id, mode = 'session', perform = 'check', .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        if (response == "ok") {
            object@session_id <- new_session_id
            return(object)
        }
        else {
            warning("Wrong session id.")
            return(object)
        }
    }
)

setGeneric("nvAttachLastSession", function(object) standardGeneric("nvAttachLastSession"))
setMethod("nvAttachLastSession",
signature(object = "NaviCell"),
    function(object) {
        response <- postForm(object@proxy_url, style='POST', id = '1', msg_id = object@msg_id, mode = 'session', perform = 'get', which = '@@', .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        #message(response)
        object <- nvAttachSession(object, response)
        return(object)
    }
)
 
setGeneric("nvGetHugoList", function(object, ...) standardGeneric("nvGetHugoList"))
setMethod("nvGetHugoList",
    signature(object = "NaviCell"),
    function(object, mod, zoom_level) {
        object <- nvIncMessageId(object)
        list_param <- list(module='', args = array(), msg_id = object@msg_id, action = 'nv_get_hugo_list')  
        str_data <- nv_make_data(nv_format_json(list_param))
        #message('get_hugo_list data: ',str_data)
        response <- postForm(object@proxy_url, style = 'POST', id = object@session_id, msg_id = object@msg_id, mode='cli2srv', perform='send_and_rcv', data=str_data, .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        #message(response)
        response <- fromJSON(response)
        object@hugo_list <- response$data
        return(object)
    }
)

setGeneric("nvGetBiotypeList", function(object, ...) standardGeneric("nvGetBiotypeList"))
setMethod("nvGetBiotypeList",
    signature(object = "NaviCell"),
    function(object, mod, zoom_level) {
        object <- nvIncMessageId(object)
        list_param <- list(module='', args = array(), msg_id = object@msg_id, action = 'nv_get_biotype_list')  
        str_data <- nv_make_data(nv_format_json(list_param))
        response <- postForm(object@proxy_url, style = 'POST', id = object@session_id, msg_id = object@msg_id, mode='cli2srv', perform='send_and_rcv', data=str_data, .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        response <- fromJSON(response)
        return(response)
    }
)

setGeneric("nvGetModuleList", function(object, ...) standardGeneric("nvGetModuleList"))
setMethod("nvGetModuleList",
    signature(object = "NaviCell"),
    function(object, mod, zoom_level) {
        object <- nvIncMessageId(object)
        list_param <- list(module='', args = array(), msg_id = object@msg_id, action = 'nv_get_module_list')  
        str_data <- nv_make_data(nv_format_json(list_param))
        response <- postForm(object@proxy_url, style = 'POST', id = object@session_id, msg_id = object@msg_id, mode='cli2srv', perform='send_and_rcv', data=str_data, .opts=curlOptions(ssl.verifypeer=F))
        response <- nv_format_response(response)
        response <- fromJSON(response)
        return(response)
    }
)



#
#  Utilities
#

# create NaviCell server command string from a list of parameters
nv_make_data <- function(json_string) {
    ret <- paste("@COMMAND ", json_string, sep = "")
    return(ret)
}

# format list of parameters to NaviCell server compatible JSON format
nv_format_json <- function(list_param) {
    data <- toJSON(list_param)
    data <- gsub("\n", '', data)
    data <- gsub(" ", "", data)
    return(data)
}

# format response obtained from the 'postForm' command
# in some cases return is 'raw' (e.g. R session in a terminal) otherwise plain text (e.g. R GUI)
nv_format_response <- function(response) {
    ret = ''
    if (class(response) == 'raw') {
        ret <- rawToChar(response)
    }
    else if (class(response) == "character") {
        ret <- response[1]
    }
    return(ret)
}

# attachLastSession
# params:  {'id': '1', 'mode': 'session', 'which': '@@', 'msg_id': 1001, 'perform': 'get'}
# params:  {'mode': 'session', 'perform': 'check', 'msg_id': 1002, 'id': '1415875286562432126560'}
testF <- function(object) {
    ret <- postForm(object@proxy_url, style='POST', id = '1', msg_id = object@msg_id, mode = 'session', perform = 'get', which = '@@', .opts=curlOptions(ssl.verifypeer=F))
    print(nv_format_response(ret))
}
