// 路径配置
require.config({
	paths: {
		echarts: 'http://echarts.baidu.com/build/dist'
	}
});

// 使用
require(
	[
		'echarts',
		'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载,
		'echarts/chart/map', // 使用柱状图就加载bar模块，按需加载,
		'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	],
	function(ec) {

		var areaMapChart = ec.init(document.getElementById('areaMapChart'));
		var areaMapChartOption = {
			title: {
				text: '围垦面积分析',
				subtext: '单位:万亩',
				x: 'center'
			},
			tooltip: {
				trigger: 'item'
			},
			legend: {
				orient: 'vertical',
				x: 'left',
				data: ['已围', '在围', '规划']
			},
			dataRange: {
				min: 0,
				max: 200,
				x: 'left',
				y: 'bottom',
				text: ['高', '低'], // 文本，默认为数值文本
				calculable: true
			},
			toolbox: {
				show: true,
				feature: {
					dataView: { show: true, readOnly: false },
					restore: { show: true },
					saveAsImage: { show: true }
				}
			},
			series: [{
					name: '已围',
					type: 'map',
					mapType: '江苏',
					roam: false,
					itemStyle: {
						normal: { label: { show: true } },
						emphasis: { label: { show: true } }
					}, //value:Math.round(Math.random() * 1000
					data: [
						{ name: '连云港市', value: 8.50 },
						{ name: '南通市', value: 42.01 },
						{ name: '盐城市', value: 33.29 }

					]
				},
				{
					name: '在围',
					type: 'map',
					mapType: '江苏',
					itemStyle: {
						normal: { label: { show: true } },
						emphasis: { label: { show: true } }
					},
					data: [
						{ name: '连云港市', value: 6.95 },
						{ name: '南通市', value: 74.22 },
						{ name: '盐城市', value: 100.88 }

					]
				},
				{
					name: '规划',
					type: 'map',
					mapType: '江苏',
					itemStyle: {
						normal: { label: { show: true } },
						emphasis: { label: { show: true } }
					},
					data: [
						{ name: '连云港市', value: 15.45 },
						{ name: '南通市', value: 116.23 },
						{ name: '盐城市', value: 134.17 }

					]
				}
			]
		};

		areaMapChart.setOption(areaMapChartOption);
	}
);