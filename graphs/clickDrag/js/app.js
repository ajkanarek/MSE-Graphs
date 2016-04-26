var app = angular.module('graphApp',[]);


// var xi = .5
// var f = .5



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
            // categories: ['0.0', '0.1', '0.2', '0.3', '0.4', '0.5', '0.6', '0.7', '0.8', '0.9', '1.0'],
            // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            max: 1,
            min: 0,
            tickAmount: 3,
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
            type: "linear"
        },

        plotOptions: {
            series: {
                point: {
                    events: {

                        drag: function (e) {
                            // Returning false stops the drag and drops. Example:
                            // console.log(e);
                            

                            $('#drag').html(
                                'Dragging <b>' + this.series.name + '</b>, <b>' + this.category + '</b> to <b>' + Highcharts.numberFormat(e.y, 2) + '</b>');
                        },
                        drop: function (e) {
                            e.preventDefault()
                            var f = e.target.y;
                            var xi = e.target.x;
                            console.log("f: " + f + " xi: " + xi);
                            A = getMatrix(xi, f);
                            var consts = gauss(A);
                            // console.log(consts);
                            // var x = .1
                            // var y = getY(x, xi, f, consts);
                            // console.log("y: " + y);
                            // console.log(chart);
                            for (var i = 0; i < chart.series[0].data.length; ++i) {
                                var x = chart.series[0].data[i].x;
                                var y = getY(x, xi, f, consts);
                                if (y < 0) {
                                    y = 0;
                                }
                                else if (y > 1) {
                                    y = 1;
                                }
                                chart.series[0].data[i].update(y);
                                // console.log(chart.series[0].data[i].y);
                            }


                            $('#drop').html(
                                'In <b>' + this.series.name + '</b>, <b>' + this.category + '</b> was set to <b>' + Highcharts.numberFormat(this.y, 2) + '</b>');
                        }
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
                // data: [0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1],
                data: [{
                    x: 0,
                    y: 0,
                    marker: {
                        enabled: false
                    },
                    draggableY: false,
                    events: {
                        mouseOver: function() {
                            console.log("triggered");
                            return false;
                        }
                    }
                }, {
                    x: .1,
                    y: .1,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .2,
                    y: .2,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .3,
                    y: .3,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .4,
                    y: .4,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .5,
                    y: .5,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .6,
                    y: .6,
                    marker: {
                        enabled: false
                    },
                    states: {
                        hover: {
                            enabled: false
                        }
                    }
                }, {
                    x: .7,
                    y: .7,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .8,
                    y: .8,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: .9,
                    y: .9,
                    marker: {
                        enabled: false
                    }
                }, {
                    x: 1,
                    y: 1,
                    draggableY: false,
                    marker: {
                        enabled: false
                    }
                }],
                draggableY: true,
                name: "Andrew's data"
            }
        ]

    });


    


}]);













