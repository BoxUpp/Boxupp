/*******************************************************************************
 *  Copyright 2014 Paxcel Technologies
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *******************************************************************************/
ScriptMapping = function(activeBoxes,data){
	$("#scriptMapping").empty();
	var width = 900, 
	height = 250;

	var svg = d3.select("#scriptMapping").append("svg")
	.attr("width", width)
	.attr("height", height)
	.append("g")
	.attr("transform", "translate(20,0)");

	createVisualization(svg,activeBoxes,data,height,width);
}

ModuleMapping = function(activeBoxes,data){
	$("#moduleMapping").empty();
	var width = 900,
	height = 250;

	var svg = d3.select("#moduleMapping").append("svg")
	.attr("width", width)
	.attr("height", height)
	.append("g")
	.attr("transform", "translate(20,0)");

	createVisualization(svg,activeBoxes,data,height,width);
}

function createVisualization(svg,activeBoxes,data,height,width){
	var totalBoxesCount=data.children.length;
	var activeBoxesCount=activeBoxes.children.length;
	var mainLineLength=(totalBoxesCount*50)+25;

	if(mainLineLength>800)
		mainLineLength=800;

	var spacingBetweenBoxes=(mainLineLength-25)/totalBoxesCount;
	var currentBoxposition=0;

	var boxCircleRadius=5;
	var mainLineColor='#2B5464';
	var activeBoxLineColor='#2B5464';
	var inactiveBoxLineColor='#c3c3c3';
	var activeBoxCircleColor='#CD8227';
	var inActiveBoxCircleColor='#c3c3c3';
	var mainCircleColor='rgb(136, 210, 247)';

	//Script Circle
	svg.append('circle')
	.attr('value',data.name)
	.attr('cx',05)
	.attr('cy',height/2)
	.attr('r',20)
	.attr('fill','none')
	.attr("stroke",mainLineColor)
	.attr("stroke-width",'3px');

	//Script Icon
	svg.append('text')
	.attr("dx", -5)
	.attr("dy", (height/2)+8)
	.attr('font-family', 'FontAwesome')
	.attr('font-size', function() { return 25+'px'} )
	.text(function() { return '\uf1c9' }); 

	//Mapping
	var i,j;
	for(i=0;i<totalBoxesCount;i++){
		currentBoxposition=currentBoxposition+spacingBetweenBoxes;
		for(j=0;j<activeBoxesCount;j++)
		{
			if(data.children[i].vagrantID==activeBoxes.children[j].vagrantID)
			{
				//active box
				svg.append("line")
				.attr("x1", function () {
					return currentBoxposition;
				})
				.attr("y1", function () {
					return height/2;
				})
				.attr("x2", function () {
					return currentBoxposition;
				})
				.attr("y2", function () {
					return (height/2)-50;
				}).attr("stroke",activeBoxLineColor)
				.attr("stroke-width",'2px');

				svg.append('circle')
				.attr('value',"Vagrant Id: "+data.children[i].vagrantID+"<br />Host Name:"+data.children[i].hostName)
				.attr('cx',currentBoxposition)
				.attr('cy',(height/2)-50)
				.attr('r',boxCircleRadius)
				.attr('fill',activeBoxCircleColor)
				.on('mouseover', function() {showData(this, d3.select(this).attr('value'));})
				.on('mouseout', function() {hideData();});

				svg.append('text')
				.attr("transform","translate("+currentBoxposition+","+(((height/2)-50)-10)+")rotate(-45)")
				.text(data.children[i].vagrantID);

				break;
			}
		}
		if(j==activeBoxes.children.length){
			//inactive box
			svg.append("line")
			.attr("x1", function () {
				return currentBoxposition;
			})
			.attr("y1", function () {
				return height/2;
			})
			.attr("x2", function () {
				return currentBoxposition;
			})
			.attr("y2", function () {
				return (height/2)+50;
			}).attr("stroke",inactiveBoxLineColor)	
			.attr("stroke-width",'2px');
			svg.append('circle')
			.attr('value',"Vagrant Id: "+data.children[i].vagrantID+"<br />Host Name: "+data.children[i].hostName)
			.attr('cx',currentBoxposition)
			.attr('cy',(height/2)+50)
			.attr('r',boxCircleRadius)
			.attr('fill',inActiveBoxCircleColor)
			.on('mouseover', function() {showData(this, d3.select(this).attr('value'));})
			.on('mouseout', function() {hideData();});

			svg.append('text')
			.attr("transform","translate("+currentBoxposition+","+(((height/2)+50)+10)+")rotate(45)")
			.text(data.children[i].vagrantID);
		}
	}

	svg.append('circle')
	.attr('cx',05)
	.attr('cy',(height)-25)
	.attr('r',boxCircleRadius)
	.attr('fill',activeBoxCircleColor);

	svg.append('text')
	.text("Mapped Boxes")
	.attr("dx", 12)
	.attr("dy", height-20);

	svg.append('circle')
	.attr('cx',05)
	.attr('cy',(height)-10)
	.attr('r',boxCircleRadius)
	.attr('fill',inActiveBoxCircleColor);

	svg.append('text')
	.text("UnMapped Boxes")
	.attr("dx", 12)
	.attr("dy", height-5);

	//main line
	svg.append("line")
	.attr("x1", function () {
		return 25;
	})
	.attr("y1", function () {
		return height/2;
	})
	.attr("x2", function () {
		return mainLineLength;
	})
	.attr("y2", function () {
		return height/2;
	}).attr("stroke",mainLineColor)
	.attr("stroke-width",'3px');

	//finishing main line
	svg.append("line")
	.attr("x1", mainLineLength)
	.attr("y1",(height/2)-7)
	.attr("x2",mainLineLength)
	.attr("y2", (height/2)+7)
	.attr("stroke",mainLineColor)
	.attr("stroke-width",'3px');

	d3.select(self.frameElement).style("height", height + "px");
	if (!$("body").find('.infobox').length) {
		$("body").append("<div class='infobox' style='display:none;'></div>");
	}
}

function showData(obj, d) {
	var coord = d3.mouse(obj);
	console.log(coord+" page:"+event.pageX+","+event.pageY);
	var infobox = d3.select(".infobox");
	infobox.style("left", ((event.pageX)-20) +"px" );
	infobox.style("top",  ((event.pageY)+20) + "px");
	$(".infobox").html(d);
	$(".infobox").show();
}

function hideData() {
	$(".infobox").hide();
}
