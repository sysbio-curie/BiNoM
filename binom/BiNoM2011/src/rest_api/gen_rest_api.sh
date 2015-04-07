#!/bin/sh
#
# gen_rest_api.sh
#

sed -e 's/#.*//' | awk '
BEGIN {
  FS="\t";
  in_table = 0;
}

substr($1, 1, 1) == "@" {
  if (in_table) {
    printf("</table>\n\n");
  }
  if ($2) {
     printf("<h3>%s</h3>\n", $2);
  }
  has_subcommand = substr($1, 2, 1) == "!";
  printf("<table border=\"1\" cellpadding=\"4\" cellspacing=\"0\" class=\"action-table\">\n");
  printf("<tr><th>Description</th><th>Action Name</th>%s<th colspan=\"4\">Arguments</th></tr>\n", (has_subcommand ? "<th>Command</th>" : ""));
  in_table = 1;
  max_args = has_subcommand ? substr($1, 3) : substr($1, 2);
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
  for (nn = start; nn <= NF; nn+=2) {
    nn_1 = nn+1;
    printf("<td><span class=\"variable\">%s</span> <span class=\"command\">:%s</span></td>", $nn, $nn_1);
    arg_cnt++;
  }
  for (nn = arg_cnt; nn < max_args; nn++) {
    printf("<td style=\"text-align: center\">-</td>");
  }
  printf("</tr>\n");
}

END {
  if (in_table) {
    printf("</table>\n\n");
  }
}'



