#!/bin/sh
#
# gen_rest_api.sh
#

sed -e 's/#.*//' -e 's/ /\&nbsp;/g' $1 | awk '
BEGIN {
  FS="\t";
  in_table = 0;
}

substr($1, 1, 1) == "@" {
  if (in_table) {
    printf("</table>\n\n");
  }
  if ($2) {
     printf("<h3>%s</h3>\n\n", $2);
  }
  has_subcommand = substr($1, 2, 1) == "!";
  max_args = has_subcommand ? substr($1, 3) : substr($1, 2);
  printf("<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\" class=\"table-contents\">\n");
  printf("<tr class=\"table-header\"><th>Description</th><th>Action Name</th>%s", (has_subcommand ? "<th>Command Argument</th>" : ""));
  if (max_args > 0) {  
    printf("<th colspan=\"8\">Arguments</th>");
  }
  printf("</tr>\n");
  in_table = 1;
  next;
}

NF > 1 {
  printf("<tr><td>%s</td><td><span class=\"command\">%s</span></td>", $1, $2);
  if (has_subcommand) {
    printf("<td><span class=\"command\">%s</span></td>", $3);
    start = 4;
  } else {
    start = 3;
  }
  arg_cnt = 0;
  example_cnt = 0;
  example = ""
  for (nn = start; nn <= NF; nn+=2) {
    nn_1 = nn+1;
    if ($nn == "@example") {
      example = (example_cnt > 0 ? example "<br/>" : "") "data=@COMMAND {\"action\": \"" $2 "\", \"args\": [" (has_subcommand ? "\"" $3 ($nn_1 ? "\", " : "\"") : "") $nn_1 "]}";
      example_cnt++;
      continue;
    }
    if ($nn_1 == "flag") {
        style = "command";
        $nn_1 = "";
    } else {
        style =  "variable";
        $nn_1 = ":" $nn_1;
    }
    
    printf("<td><span class=\"%s\">%s</span>&nbsp;<span class=\"command\">%s</span></td>", style, $nn, $nn_1);
    arg_cnt++;
  }
  for (nn = arg_cnt; nn < max_args; nn++) {
    printf("<td style=\"text-align: center\">-</td>");
  }
  printf("</tr>\n");
  if (example_cnt > 0) {
     printf("<tr class=\"table-example\"><td style=\"font-size: small; text-align: right\">Example%s</td><td colspan=\"5\">%s</td></tr>\n", (example_cnt > 1 ? "s" : ""), example);
  }
}

END {
  if (in_table) {
    printf("</table>\n\n");
  }
}'



