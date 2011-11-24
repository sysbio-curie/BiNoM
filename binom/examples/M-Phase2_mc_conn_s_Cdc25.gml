Creator"Cytoscape"
Version	1.0
graph	[
	node	[
		root_index	0
		id	0
		graphics	[
			x	0.0
			y	0.0
			w	30.0
			h	30.0
			fill	"#ffffff"
			type	"rectangle"
			outline	"#000000"
			outline_width	1
		]
		label	"Cdc25"
	]
	node	[
		root_index	1
		id	1
		graphics	[
			x	0.0
			y	0.0
			w	30.0
			h	30.0
			fill	"#ffffff"
			type	"diamond"
			outline	"#000000"
			outline_width	1
		]
		label	"re15"
	]
	node	[
		root_index	2
		id	2
		graphics	[
			x	0.0
			y	0.0
			w	30.0
			h	30.0
			fill	"#ffffff"
			type	"rectangle"
			outline	"#000000"
			outline_width	1
		]
		label	"Cdc25_pho_active"
	]
	node	[
		root_index	3
		id	3
		graphics	[
			x	0.0
			y	0.0
			w	30.0
			h	30.0
			fill	"#ffffff"
			type	"diamond"
			outline	"#000000"
			outline_width	1
		]
		label	"re16"
	]
	edge	[
		root_index	-1
		target	1
		source	0
		label	"LEFT"
	]
	edge	[
		root_index	-2
		target	2
		source	1
		label	"RIGHT"
	]
	edge	[
		root_index	-3
		target	3
		source	2
		label	"LEFT"
	]
	edge	[
		root_index	-4
		target	0
		source	3
		label	"RIGHT"
	]
]
