var app = angular.module('graphApp',[]);

app.controller('graphCtrl', ['$scope', function($scope) {
    // widget.notifyContentIsReady();
    console.log("App controller working!");



    $scope.typeTemp = function() {
        var val = $scope.text_input;
        if (val >= 0 && val <= 1) {
            return;  
        }
        else {
            alert("please enter a valid temperature");
        }
    }




    var data = {
        // labels: ["0", ".5", "1"],
        labels: ["0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"],
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(220,220,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: [0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1]
            }
            // {
            //     label: "My Second dataset",
            //     fillColor: "rgba(151,187,205,0.2)",
            //     strokeColor: "rgba(151,187,205,1)",
            //     pointColor: "rgba(151,187,205,1)",
            //     pointStrokeColor: "#fff",
            //     // pointHighlightFill: "#fff",
            //     // pointHighlightStroke: "rgba(151,187,205,1)",
            //     data: [0, .5, 1]
            // }
        ]
      };
      var options = {
        scaleOverride: true,
        scaleSteps: 10,
        scaleStepWidth: .1,
        scaleStartValue: 0,

        ///Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines : true,

        //String - Colour of the grid lines
        scaleGridLineColor : "rgba(0,0,0,.05)",

        //Number - Width of the grid lines
        scaleGridLineWidth : 1,

        //Boolean - Whether to show horizontal lines (except X axis)
        scaleShowHorizontalLines: true,

        //Boolean - Whether to show vertical lines (except Y axis)
        scaleShowVerticalLines: true,

        //Boolean - Whether the line is curved between points
        bezierCurve : true,

        //Number - Tension of the bezier curve between points
        bezierCurveTension : 0.4,

        //Boolean - Whether to show a dot for each point
        pointDot : true,

        //Number - Radius of each point dot in pixels
        pointDotRadius : 7,

        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth : 1,

        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius : 5,

        //Boolean - Whether to show a stroke for datasets
        datasetStroke : true,

        //Number - Pixel width of dataset stroke
        datasetStrokeWidth : 2,

        //Boolean - Whether to fill the dataset with a colour
        datasetFill : false,

        //String - A legend template
        legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
    };


    var ctx = document.getElementById("myChart").getContext("2d");
    // var myLineChart = new Chart(ctx).Line(data, options);
    var myLineChart = new Chart(ctx).Line(data, options);
    console.log(myLineChart);


    var canvas = document.getElementById("myChart");

    canvas.addEventListener("click", getPosition, false);








    $scope.clicked = false;
    var clicked = false;

    YMIN = 12;
    YMAX = 377;
    YAXISSCALE = 36.5;
    FIVE = {
        x: 208,
        y: 194.5,
        increment: function() {
            if (this.y >= (YMIN + YAXISSCALE)) {
                this.y -= YAXISSCALE;
            }
        },
        decrement: function() {
            if (this.y <= (YMAX - YAXISSCALE)) {
                this.y += YAXISSCALE;
            }
        },
        updateY: function(val) {
            this.y = val;
        }
    };

    function getPosition(event) {
        event.preventDefault();
        var activePoints = myLineChart.getPointsAtEvent(event);
        if (activePoints.length == 0) return;
        console.log(activePoints);
        var dataX = activePoints[0].x;
        var dataY = activePoints[0].y;
        var x = event.x;
        var y = event.y;
        console.log("dataX: " + dataX + " dataY: " + dataY);
        console.log("x: " + x + " y: " + y);
        console.log(".5 x: " + FIVE.x + " .5 y: " + FIVE.y);


        

            if (!clicked) {
                if ( (Math.abs(x-FIVE.x) < 15) && (Math.abs(y-FIVE.y) < 15) ){
                    clicked = true;
                    $scope.clicked = true;
                    document.getElementById("clicked").innerHTML = "true";

                    // for (var i = 0; i < myLineChart.datasets[0].points.length; ++i) {
                    //     clicked = true;
                    //     if (myLineChart.datasets[0].points[i] == activePoints[0]) {
                    //         myLineChart.datasets[0].points[i].value += .1;
                    //         // console.log(myLineChart.datasets[0].points[i].fillColor);
                    //         // myLineChart.datasets[0].points[i].fillColor = "red";
                    //         // console.log(myLineChart.datasets[0].points[i].fillColor);
                    //         clicked = true;
                    //         FIVE.increment();

                    //         myLineChart.update();
                    //     }
                    // }
                }
            }
            else { //36.5
                console.log("clicked after click");
                if ( (Math.abs(x-FIVE.x) < 15) ) {
                    if (y <= 387 && y >= 367) {
                        console.log("clicked 0");
                        myLineChart.datasets[0].points[5].value = 0;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(377);
                        clicked = false;
                    }
                    else if (y <= 350.5 && y >= 330.5) {
                        myLineChart.datasets[0].points[5].value = .1;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(340.5);
                        clicked = false;
                    }
                    else if (y <= 314 && y >= 294) {
                        myLineChart.datasets[0].points[5].value = .2;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(304);
                        clicked = false;
                    }
                    else if (y <= 277.5 && y >= 257.5) {
                        myLineChart.datasets[0].points[5].value = .3;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(267.5);
                        clicked = false;
                    }
                    else if (y <= 241 && y >= 221) {
                        myLineChart.datasets[0].points[5].value = .4;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(231);
                        clicked = false;
                    }
                    else if (y <= 204.5 && y >= 184.5) {
                        myLineChart.datasets[0].points[5].value = .5;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(194.5);
                        clicked = false;
                    }
                    else if (y <= 168 && y >= 148) {
                        myLineChart.datasets[0].points[5].value = .6;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(158);
                        clicked = false;
                    }
                    else if (y <= 131.5 && y >= 111.5) {
                        myLineChart.datasets[0].points[5].value = .7;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(121.5);
                        clicked = false;
                    }
                     else if (y <= 95 && y >= 75) {
                        myLineChart.datasets[0].points[5].value = .8;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(85);
                        clicked = false;
                    }
                     else if (y <= 58.8 && y >= 38.8) {
                        myLineChart.datasets[0].points[5].value = .9;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(48.5);
                        clicked = false;
                    }
                    else if (y >= 2 && y <= 22) {
                        myLineChart.datasets[0].points[5].value = 1.0;
                        document.getElementById("clicked").innerHTML = "false";
                        FIVE.updateY(12);
                        clicked = false;
                    }

                    myLineChart.update();
                    // for (var i = 0; i < myLineChart.datasets[0].points.length; ++i) {
                    //     if (myLineChart.datasets[0].points[i] == activePoints[0]) {
                    //         myLineChart.datasets[0].points[i].value -= .1;
                    //         // console.log(myLineChart.datasets[0].points[i].fillColor);
                    //         // myLineChart.datasets[0].points[i].fillColor = "black";
                    //         clicked = false;
                    //         FIVE.decrement();
                    //         myLineChart.update();
                    //     }
                    // }
                }
            }
      }


}]);













