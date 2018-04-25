// 路径配置

$(function(){
	
	
	
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
		
		
	var areaPieChart = ec.init(document.getElementById(mapArea));
	var areaPieChartOption = {
		title: {
			text: '按行政区划统计区块数量',
			subtext: '单位:个',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c}({d}%)"
		},
		toolbox: {
			show: true,
			feature: {
				dataView: { show: true, readOnly: false },
				restore: { show: true },
				saveAsImage: { show: true }
			}
		},
		legend: {
			orient: 'vertical',
			x: "left",
			data: ['连云港市', '盐城市', '南通市']
		},
		series: [{
			name: '行政区划',
			type: 'pie',
			radius: '55%',
			center: ['50%', '60%'],
			data: [
				{ value: 6, name: '连云港市' },
				{ value: 21, name: '盐城市' },
				{ value: 27, name: '南通市' }
			],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
	// 为echarts对象加载数据 
	areaPieChart.setOption(areaPieChartOption);
	

	}
);

	
});

/**
 *  按照行政区划统计区块个数信息
 */
function showCityPieNumChart(ec,divId) {
	// 基于准备好的dom，初始化echarts图表
	

}


/**
 *  按照行政区划统计区块面积信息
 */
function showCityPieAreaChart(ec,divId) {
	// 基于准备好的dom，初始化echarts图表
	var areaPieChart = ec.init(document.getElementById(divId));
	var areaPieChartOption = {
		title: {
			text: '按行政区划统计区块面积',
			subtext: '单位:万亩',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c}({d}%)"
		},
		toolbox: {
			show: true,
			feature: {
				dataView: { show: true, readOnly: false },
				restore: { show: true },
				saveAsImage: { show: true }
			}
		},
		legend: {
			orient: 'vertical',
			x: "left",
			data: ['连云港市', '盐城市', '南通市']
		},
		series: [{
			name: '行政区划',
			type: 'pie',
			radius: '55%',
			center: ['50%', '60%'],
			data: [
				{ value: 42.6, name: '连云港市' },
				{ value: 34.2, name: '盐城市' },
				{ value: 11.9, name: '南通市' }
			],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
	// 为echarts对象加载数据 
	areaPieChart.setOption(areaPieChartOption);
	}



