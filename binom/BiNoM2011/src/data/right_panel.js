/**
  *    Stuart Pook, Copyright (C) 2011 Institut Curie
  *
  *    This program is free software: you can redistribute it and/or modify
  *    it under the terms of the GNU General Public License as published by
  *    the Free Software Foundation, either version 3 of the License, or
  *    (at your option) any later version.
  *
  *    This program is distributed in the hope that it will be useful,
  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *    GNU General Public License for more details.
  *
  *    You should have received a copy of the GNU General Public License
  *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  *    $Id: click_map.js 11067 2011-11-02 17:03:35Z spook $
 */
function start_right_hand_panel(selector, source) {
	var filter = ".modification";
	$(selector).jstree({
		"themes" : {
			"theme" : "default",
			"dots" : false,
			"icons" : false
		},
		core : { "animation" : 200 },
		"xml_data" : {
			"ajax" : {
				"url" : source
			},
			"xsl" : "nest"
		},
		plugins : [ "themes", "xml_data", "ui", "checkbox" ]
	}).bind("uncheck_node.jstree", function(event, data) {
		var f = function(index, element) { console.log("--" + index + " " + $(element).attr("id")); };
//		if (data.args[0].parentNode.parentNode.id == "")
			$(this).jstree("get_unchecked",data.args[0],true).filter(filter).each(f);
			$(data.args[0].parentNode.parentNode).filter(filter).each(f);
//			console.log("- " + data.args[0].parentNode.parentNode.id);
	}).bind("check_node.jstree", function(event, data) {
//		console.log(event.type + ' checked l=' +  data.args.length + ' ' + data.args[0].parentNode.id + ' href=' + data.rslt.obj.attr('href') + " " + data.inst.get_text(data.rslt.obj));
		
		var f = function(index, element) { console.log("++" + index + " " + $(element).attr("id")); };
		$(this).jstree("get_checked",data.args[0],true).filter(filter).each(f);
		
		$(data.args[0].parentNode.parentNode).filter(filter).each(f);
//		console.log("+  '" + data.args[0].parentNode.parentNode.id + "' '" + data.args[0].parentNode.parentNode.className + "'");
	});

};
