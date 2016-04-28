var app = angular.module('graphApp',[]);

var p0 = [0, 0]; var p1 = [.15, .8]; var p2 = [.7, .7]; var p3 = [1, 1]; var num_points = 200;

app.controller('graphCtrl', ['$scope', function($scope) {
    // widget.notifyContentIsReady();
    console.log("App controller working!");


    var chart = new Highcharts.Chart({

        chart: {
            renderTo: 'container',
            animation: false
        },
        
        title: {
            text: 'Highcharts draggable points',
            x: -20
        },

        xAxis: {
            max: 1,
            min: 0,
            tickAmount: 11,
            type: "linear"
        },
        yAxis: {
            title: {
                text: "Values"
            },
            allowDecimals: true,
            min: 0,
            max: 1,
            tickAmount: 11,
            type: "linear",
            lineWidth: 1,
        },

        plotOptions: {
            series: {
                point: {
                    events: {
                        drag: function (e) {
                            // Returning false stops the drag and drops. Example:

                            // check if x and y are not on grid
                            if (e.target.x >= 1 || e.target.x <= 0 || e.target.y >= 1 || e.target.y <= 0) {
                                return;
                            }

                            $('#drag').html(
                                'Dragging <b>' + this.series.name + '</b>, <b>' + this.category + '</b> to <b>' + Highcharts.numberFormat(e.y, 2) + '</b>');
                        },
                        drop: function (e) {
                            e.preventDefault();

                            var x = e.target.x;
                            var y = e.target.y;
                            

                            // check if x and y are not on grid
                            if (x <= 0) { e.target.x = 0; }
                            else if (x >= 1) { e.target.x = 1; }
                            if (y <= 0) { e.target.y = 0; }
                            else if (y >= 1) { e.target.y = 1; }


                            if (e.target.id === "p1") {
                                p1 = [e.target.x, e.target.y];
                                chart.series[2].setData([]);
                                addSeries(p0, p1, p2, p3, num_points);
                            }
                            else if (e.target.id === "p2") {
                                var mid = (e.target.x + e.target.y) / 2;
                                console.log("mid: " + mid);
                                var y = mid;
                                var x = mid;
                                chart.series[1].data[0].update([x, y]);
                                console.log("x: " + e.target.x + " y: " + e.target.y);
                                p2 = [e.target.x, e.target.y];
                                chart.series[2].setData([]);
                                addSeries(p0, p1, p2, p3, num_points);

                            }


                            $('#drop').html(
                                'In <b>' + this.series.name + '</b>, <b>' + this.category + '</b> was set to <b>' + Highcharts.numberFormat(this.y, 2) + '</b>');
                        },

                    }
                },
                stickyTracking: false
            },
            line: {
                cursor: 'ns-resize'
            }
        },

        tooltip: {
            yDecimals: 10
        },

        series: [
            {
                data: [{
                    x: 0,
                    y: 0,
                    draggableY: false,
                    draggableX: false,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .1,
                    y: .8,
                    id: "p1"
                }],
                draggableY: true,
                draggableX: true,
                name: "Andrew's data"
            },
            {
                data: [{
                    x: .7,
                    y: .7,
                    id: "p2"
                }, {
                    x: 1,
                    y: 1,
                    draggableY: false,
                    draggableX: false,
                    marker: {
                        enabled: false
                    }
                }],
                draggableY: true,
                draggableX: true,
                name: "Andrew's data"
            },
            {
                id: "plot",
                animation: false
            } 
        ]
    });

    window.onload = addSeries(p0, p1, p2, p3, num_points);


    


}]);













