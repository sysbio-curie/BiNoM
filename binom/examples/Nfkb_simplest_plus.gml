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
		label	"NFkB"
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
		label	"re1"
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
		label	"IkB_alpha"
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
		label	"NFkB/IkB_alpha"
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
			type	"diamond"
			outline	"#000000"
			outline_width	1
		]
		label	"re2"
	]
	node	[
		root_index	5
		id	5
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
		label	"NFkB_nucleus"
	]
	node	[
		root_index	6
		id	6
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
		label	"IkB_alpha_nucleus"
	]
	node	[
		root_index	7
		id	7
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
		label	"re3"
	]
	node	[
		root_index	8
		id	8
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
		label	"re5"
	]
	node	[
		root_index	9
		id	9
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
	node	[
		root_index	10
		id	10
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
		label	"Kinase"
	]
	node	[
		root_index	11
		id	11
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
		label	"re17"
	]
	node	[
		root_index	12
		id	12
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
		label	"IkB_alpha_Ser32_pho"
	]
	node	[
		root_index	13
		id	13
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
		label	"catalyst"
	]
	node	[
		root_index	14
		id	14
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
		label	"simple"
	]
	node	[
		root_index	15
		id	15
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
		label	"re18"
	]
	edge	[
		root_index	-1
		target	1
		source	0
		label	"LEFT"
	]
	edge	[
		root_index	-2
		target	1
		source	2
		label	"LEFT"
	]
	edge	[
		root_index	-3
		target	3
		source	1
		label	"RIGHT"
	]
	edge	[
		root_index	-4
		target	4
		source	0
		label	"LEFT"
	]
	edge	[
		root_index	-5
		target	5
		source	4
		label	"RIGHT"
	]
	edge	[
		root_index	-6
		target	7
		source	6
		label	"LEFT"
	]
	edge	[
		root_index	-7
		target	2
		source	7
		label	"RIGHT"
	]
	edge	[
		root_index	-8
		target	8
		source	5
		label	"LEFT"
	]
	edge	[
		root_index	-9
		target	6
		source	8
		label	"RIGHT"
	]
	edge	[
		root_index	-10
		target	5
		source	8
		label	"RIGHT"
	]
	edge	[
		root_index	-11
		target	9
		source	3
		label	"LEFT"
	]
	edge	[
		root_index	-12
		target	0
		source	9
		label	"RIGHT"
	]
	edge	[
		root_index	-13
		target	9
		source	10
		label	"CATALYSIS"
	]
	edge	[
		root_index	-14
		target	11
		source	2
		label	"LEFT"
	]
	edge	[
		root_index	-15
		target	12
		source	11
		label	"RIGHT"
	]
	edge	[
		root_index	-16
		target	11
		source	13
		label	"CATALYSIS"
	]
	edge	[
		root_index	-17
		target	11
		source	14
		label	"INHIBITION"
	]
	edge	[
		root_index	-18
		target	15
		source	12
		label	"LEFT"
	]
]
