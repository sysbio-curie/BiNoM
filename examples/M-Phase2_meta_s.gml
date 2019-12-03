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
			type	"octagon"
			outline	"#000000"
			outline_width	2
		]
		label	"Cdc2_1c1/Cdc2_2c1"
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
			type	"rectangle"
			outline	"#000000"
			outline_width	1
		]
		label	"APC/Slp1_active"
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
			type	"diamond"
			outline	"#000000"
			outline_width	1
		]
		label	"re15"
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
			type	"rectangle"
			outline	"#000000"
			outline_width	1
		]
		label	"Cdc25"
	]
	node	[
		root_index	4
		id	4
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
	edge	[
		root_index	-1
		target	0
		source	1
		label	"CATALYSIS"
	]
	edge	[
		root_index	-2
		target	2
		source	0
		label	"CATALYSIS"
	]
	edge	[
		root_index	-3
		target	0
		source	4
		label	"CATALYSIS"
	]
	edge	[
		root_index	-4
		target	2
		source	3
		label	"LEFT"
	]
	edge	[
		root_index	-5
		target	4
		source	2
		label	"RIGHT"
	]
	edge	[
		root_index	-6
		target	1
		source	0
		label	"CATALYSIS"
	]
	edge	[
		root_index	-7
		target	3
		source	4
		label	"STATE_TRANSITION"
	]
]
