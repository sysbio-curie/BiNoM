#
# rest_api.cmd
#

# @example are currently commented

@3	Navigation and Zooming
Set map center	nv_set_center	MAP_CENTER | MAP_EAST<br/>MAP_SOUTH | MAP_NORTH<br/>MAP_SOUTH_WEST | MAP_NORTH_EAST<br/>MAP_SOUTH_EAST	flag	@example	"MAP_CENTER"	@example	"MAP_SOUTH_EAST"
Set map center (absolute position)	nv_set_center	ABSOLUTE	flag	posx	integer	posy	integer	@example	"ABSOLUTE", 100, 300
Move map center	nv_set_center	RELATIVE	flag	posx	integer	posy	integer	@example	"RELATIVE", -10, -10
Set zoom level	nv_set_zoom	zoom level	integer	@example	10

@3	Entity Selection
Select an entity	nv_select_entity	entity identifier	string	select | toggle	flag	center	boolean	@example	"AKT1", "toggle", true
Find entities	nv_find_entities_entity	entity pattern	string	open bubble	boolean	@example	"AK*"	@example	"AKT1", true
Uncheck all entities	nv_uncheck_all_entities	@example	
Unhighlight all entities	nv_unhighlight_all_entities	@example

@!2	Heatmap Editor
Open heatmap editor	nv_heatmap_editor_perform	open	@example	
Close heatmap editor	nv_heatmap_editor_perform	close	@example
Apply changes	nv_heatmap_editor_perform	apply	@example
Apply changes and close	nv_heatmap_editor_perform	apply_and_close	@example
Cancel changes and close	nv_heatmap_editor_perform	cancel	@example
Select sample or group	nv_heatmap_editor_perform	select_sample	column num	integer	sample name	string	@example	1, "OVARY_H123"
Select datatable	nv_heatmap_editor_perform	select_datatable	row num	integer	datatable name	string	@example	1, "MyExpression"
Clear samples	nv_heatmap_editor_perform	clear_samples	@example
Select all samples	nv_heatmap_editor_perform	all_samples	@example
Select all groups	nv_heatmap_editor_perform	all_groups	@example
Import samples from barplot	nv_heatmap_editor_perform	from_barplot	@example
Set transparency	nv_heatmap_editor_perform	set_transparency	value	integer [1:100]	@example	90

@4	Datatable Import
Import a datatable from a URL	nv_import_datatables	biotype	biotype	datatable name	string	ignored argument	string	URL	string	@example	"Gene list", "MyGenes", "", "https://www.mydomain.net/my_genes.tsv"
Import a datatable from data	nv_import_datatables	biotype	biotype	datatable name	string	ignored argument	string	data	string beginning with @DATA\n	@example	"Gene list", "MyGenes", "", "@DATA\nABL1\nABR\nACACA\nACACB\nACAD9\nACHE\nACLY"

@!2	Sample Annotation Import
Open sample annotation dialog	nv_sample_annotation_perform	open	@example
Close sample annotation dialog	nv_sample_annotation_perform	close	@example
Apply changes	nv_sample_annotation_perform	apply	@example
Cancel changes and close	nv_sample_annotation_perform	cancel	@example
Import sample annotations from a URL	nv_sample_annotation_perform	import	URL	string	@example	"http://www.mydomain.net/my_sample_annotations.tsv"
Import sample annotations from data	nv_sample_annotation_perform	import	data	string beginning with @DATA\n
Select/Unselect an annotation	nv_sample_annotation_perform	select_annotation	annot name	string	checked	boolean	@example	@example	"Tissue", true	@example	"Cancer Type", false

@!0	MyData Dialog Management
Open MyData dialog	nv_mydata_perform	open	@example
Close MyData dialog	nv_mydata_perform	close	@example
Set "Datatables" tab active	nv_mydata_perform	select_datatables	@example
Set "Samples" tab active	nv_mydata_perform	select_samples	@example
Set "Genes" tab active	nv_mydata_perform	select_genes	@example
Set "Groups" tab active	nv_mydata_perform	select_groups	@example
Set "Modules" tab active	nv_mydata_perform	select_modules	@example

@!2	Drawing Configuration Dialog
Open drawing configuration dialog	nv_drawing_config_perform	open	@example
Close drawing configuration dialog	nv_drawing_config_perform	close	@example
Apply changes drawing configuration dialog	nv_drawing_config_perform	apply	@example
Apply changes and close drawing configuration dialog	nv_drawing_config_perform	apply_and_close	@example
Cancel changes and close drawing configuration dialog	nv_drawing_config_perform	cancel	@example
Select heatmap display	nv_drawing_config_perform	select_heatmap	checked	boolean	@example	true
Select barplot display	nv_drawing_config_perform	select_barplot	checked	boolean	@example	false
Select glyph display	nv_drawing_config_perform	select_glyph	glyph num	integer	checked	boolean	@example	1, true	@example	2, false
Select map staining display	nv_drawing_config_perform	select_map_staining	checked	boolean	@example	true
Select "Display All Genes"	nv_drawing_config_perform	display_all_genes	@example
Select "Display Selected Genes"	nv_drawing_config_perform	display_selected_genes	@example

@!2	Barplot Editor
Open barplot editor	nv_barplot_editor_perform	open	@example
Close barplot editor	nv_barplot_editor_perform	close	@example
Apply changes	nv_barplot_editor_perform	apply	@example
Apply changes and close	nv_barplot_editor_perform	apply_and_close	@example
Cancel changes and close	nv_barplot_editor_perform	cancel	@example
Select sample or group	nv_barplot_editor_perform	select_sample	column num	integer	sample name	string	@example	2, "OVARY_H123"
Select datatable	nv_barplot_editor_perform	select_datatable	datatable name	string
Clear samples	nv_barplot_editor_perform	clear_samples	@example	"MyExpression"
Select all samples	nv_barplot_editor_perform	all_samples	@example
Select all groups	nv_barplot_editor_perform	all_groups	@example
Import samples from heatmap	nv_barplot_editor_perform	from_heatmap
Set transparency	nv_barplot_editor_perform	set_transparency	value	integer [1:100]

@!2	Glyph Editor
Open glyph editor	nv_glyph_editor_perform	open	glyph num	integer	@example	1
Close glyph editor	nv_glyph_editor_perform	close	glyph num	integer	@example	1
Apply changes	nv_glyph_editor_perform	apply	glyph num	integer	@example	1
Apply changes and close	nv_glyph_editor_perform	apply_and_close	glyph num	integer	@example	1
Cancel changes and close	nv_glyph_editor_perform	cancel	glyph num	integer	@example	1
Select sample or group	nv_glyph_editor_perform	select_sample	glyph num	integer	sample name	string	@example	1, "OVARY_H123"
Select shape datatable	nv_glyph_editor_perform	select_datatable_shape	glyph num	integer	datatable name	string	@example	1, "MyExpression"
Select color datatable	nv_glyph_editor_perform	select_datatable_color	glyph num	integer	datatable name	string	@example	1, "MyCopyNumber"
Select size datatable	nv_glyph_editor_perform	select_datatable_size	glyph num	integer	datatable name	string	@example	1, "MyMutations"
Set transparency	nv_glyph_editor_perform	set_transparency	glyph num	integer	value	integer [1:100]	@example	1, 99

@!1	Map Staining Editor
Open map staining editor	nv_map_staining_editor_perform	open	@example
Close map staining editor	nv_map_staining_editor_perform	close	@example
Apply changes	nv_map_staining_editor_perform	apply	@example
Apply changes and close	nv_map_staining_editor_perform	apply_and_close	@example
Cancel changes and close	nv_map_staining_editor_perform	cancel	@example
Select sample or group	nv_map_staining_editor_perform	select_sample	sample name	string	@example	"OVARY_H123"
Select datatable	nv_map_staining_editor_perform	select_datatable	datatable name	string	@example	"MyExpression"
Set transparency	nv_map_staining_editor_perform	set_transparency	value	integer [1:100]	@example	90

@!5	Display Continuous Configuration Editor
Open editor	nv_display_continuous_config_perform	open	datatable name	string	shape|size|color	flag	@example	"MyExpression", "shape"
Close editor	nv_display_continuous_config_perform	close	datatable name	string	shape|size|color	flag	@example	"MyExpression", "size"
Apply changes	nv_display_continuous_config_perform	apply	datatable name	string	shape|size|color	flag	@example	"MyExpression", "color"
Apply changes and close	nv_display_continuous_config_perform	apply_and_close	datatable name	string	shape|size|color	flag	@example	"MyExpression", "shape"
Cancel changes and close	nv_display_continuous_config_perform	cancel	datatable name	string	shape|size|color	flag	@example	"MyExpression", "color"
Set step count	nv_display_continuous_config_perform	step_count_change	sample|group	flag	shape|size|color	flag	datatable name	string	step count	integer	@example	""sample", "color", "MyExpression", 4
Set absolute value mode	nv_display_continuous_config_perform	set_sample_absval	shape|size|color	flag	datatable name	string	checked	boolean	@example	"size", "MyExpression", true

Set sample method	nv_display_continuous_config_perform	set_sample_method	shape|size|color	flag	datatable name	string	method	string	@example	"size", "MyExpression", 3

Set group method	nv_display_continuous_config_perform	set_group_method	shape|size|color	flag	datatable name	string	method	string	@example	"size", "MyExpression", 3

Set input value	nv_display_continuous_config_perform	set_input_value	datatable name	string	shape|size|color	flag	sample|group	flag	index	integer	value	double	@example	"size", "sample", 2, 12.345

Set selection color	nv_display_continuous_config_perform	set_input_color	datatable name	string	color	flag	sample|group	flag	index	string	color	integer	@example	"color, "group", 3, "FFFFFF"

Set selection size	nv_display_continuous_config_perform	set_select_size	datatable name	string	size	flag	sample|group	flag	index	integer	size	integer	@example	"size", "sample", 2, 8

Set selection shape	nv_display_continuous_config_perform	set_select_shape	datatable name	string	shape	flag	sample|group	flag	index	integer	shape	integer	@example	"shape", "sample", 4

Switch to "Sample" tab	nv_display_continuous_config_perform	switch_sample_tab	datatable name	string	shape|size|color	flag	@example	"MyExpression", "shape"	@example	"MyCopyNumber", "size"
Switch to "Group" tab	nv_display_continuous_config_perform	switch_sample_group	datatable name	string	shape|size|color	flag	@example	"MyExpression", "color"


@!5	Display Unordered Discrete Configuration Editor
Open editor	nv_display__unordered_discrete_config_perform	open	datatable name	string	shape|size|color	flag	@example	"MyMutation", "shape"	@example	"MyMutation", "size"
Close editor	nv_display__unordered_discrete_config_perform	close	datatable name	string	shape|size|color	flag	@example	"MyMutation", "shape"	@example	"MyMutation", "size"
Apply changes	nv_display__unordered_discrete_config_perform	apply	datatable name	string	shape|size|color	flag	@example	"MyMutation", "color"	@example	"MyMutation", "size"
Apply changes and close	nv_display__unordered_discrete_config_perform	apply_and_close	datatable name	string	shape|size|color	flag	@example	"MyMutation", "color"	@example	"MyMutation", "size"
Cancel changes and close	nv_display__unordered_discrete_config_perform	cancel	datatable name	string	shape|size|color	flag	@example	"MyMutation", "color"	@example	"MyMutation", "size"

Open/Close advanced configuration	nv_display_unordered_discrete_config_perform	set_advanced_configuration	datatable name	string	shape|size|color	flag	checked	boolean	@example	"MyMutation", "shape", "color", true

Set discrete value	nv_display_unordered_discrete_config_perform	set_discrete_value	datatable name	string	shape|size|color	flag	sample|group	flag	index	integer	value	double	@example	"MyMutation", "shape", "sample", 2, 4

Set discrete color	nv_display_unordered_discrete_config_perform	set_discrete_color	datatable name	string	color	flag	sample|group	flag	index	string	color	integer	@example	"MyMutation", "color", "sample", 3, "FF0000"

Set discrete size	nv_display_unordered_discrete_config_perform	set_discrete_size	datatable name	string	size	flag	sample|group	flag	index	integer	size	integer	@example	"MyMutation", "sample", 3, 10

Set discrete shape	nv_display_unordered_discrete_config_perform	set_discrete_shape	datatable name	string	shape	flag	sample|group	flag	index	integer	shape	integer	@example	"MyMutation", "shape", "sample", 2, 5

Set discrete condition	nv_display_unordered_discrete_config_perform	set_discrete_cond	datatable name	string	shape|size|color	flag	sample|group	flag	index	integer	condition	integer	@example	"MyMutation", "shape", "sample", 3, 2

Switch to "Sample" tab	nv_display_unordered_discrete_config_perform	switch_sample_tab	datatable name	string	shape|size|color	flag	@example	"MyMutation", "size"
Switch to "Group" tab	nv_display_unordered_discrete_config_perform	switch_sample_group	datatable name	string	shape|size|color	flag	@example	"MyMutation", "shape"

@!5	Message Display
Open message window	nv_notice_perform	open	@example
Close message window	nv_notice_perform	close	@example
Open message window and display a message	nv_notice_perform	set_message_and_open	header	string	message	string	position	string	width	integer	height	integer	@example	"Notice", "Type AK* in the search form and type returns", "top-left", 200, 200

@0	Getting Information from the Server
Get HUGO list in map	nv_get_hugo_list	@example
Get module list	nv_get_module_list	@example
Get imported datatables	nv_get_datatable_list	@example
Get genes in imported datatables	nv_get_datatable_gene_list	@example
Get samples in imported datatables	nv_get_datatable_sample_list	@example
Get biotype list in NaviCell	nv_get_biotype_list	@example
Get command history	nv_get_command_history	@example

@0	Client/Server Synchronisation
Returns true when server is ready	nv_is_ready	@example
Returns true when datatable or sample are imported	nv_is_imported	@example
