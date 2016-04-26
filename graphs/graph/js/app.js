k = 8.31

var app = angular.module('graphApp',[]);

app.controller('graphCtrl', ['$scope', function($scope) {
  // widget.notifyContentIsReady();
  console.log("App controller working!");

  $scope.curr_temp = 300;
  $scope.curr_alpha = 0;
  $scope.text_input = $scope.curr_temp;
  var curr_alphaP = $scope.curr_alpha * $scope.curr_temp * k;

  var data = {
    // A labels array that can contain any sort of values
    labels: ['0', '.1', '.2', '.3', '.4', '.5', '.6', '.7', '.8', '.9', '1'],
    // labels: ['0', '.25', '.5', '.75', '1'],
    // Our series array that contains series objects or in this case series data arrays
    series: [
      {
        name: 'My Graph',
        data: [
          {value: 0, meta: 0},
          {value: .1, meta: .1},
          {value: .2, meta: .2},
          {value: .3, meta: .3},
          {value: .4, meta: .4},
          {value: .5, meta: .5},
          {value: .6, meta: .6},
          {value: .7, meta: .7},
          {value: .8, meta: .8},
          {value: .9, meta: .9},
          {value: 1.0, meta: 1.0}
        ]
      }
    ]
  };

  // As options we currently only set a static size of 300x200 px. We can also omit this and use aspect ratio containers
  // as you saw in the previous example
  var options = {
    width: 300,
    height: 200,
    high: 1,
    low: 0,
    scaleMinSapce: 20,
     // Don't draw the line chart points
    showPoint: false,
    // Disable line smoothing
    lineSmooth: true,
    // X-Axis specific configuration
    axisX: {
      // We can disable the grid for this axis
      showGrid: true,
      // and also don't show the label
      showLabel: true
    },
    // Y-Axis specific configuration
    axisY: {
      // Lets offset the chart a bit from the labels
      offset: 60,
      showGrid: true
      // The label interpolation function enables you to modify the values
      // used for the labels on each axis. Here we are converting the
      // values into million pound.
      // labelInterpolationFnc: function(value) {
      //   return value;
      // }

    }
    // plugins: [
    //   Chartist.plugins.ctAxisTitle({
    //     axisX: {
    //       axisTitle: 'Time (mins)',
    //       axisClass: 'ct-axis-title',
    //       offset: {
    //         x: 0,
    //         y: 50
    //       },
    //       textAnchor: 'middle'
    //     },
    //     axisY: {
    //       axisTitle: 'Goals',
    //       axisClass: 'ct-axis-title',
    //       offset: {
    //         x: 0,
    //         y: 0
    //       },
    //       textAnchor: 'middle',
    //       flipTitle: false
    //     }
    //   })
    // ]
  };

  // Create a new line chart object where as first parameter we pass in a selector
  // that is resolving to our chart container element. The Second parameter
  // is the actual data object.
  var myChart = new Chartist.Line('#graph', data, options);

  console.log(data);
  // console.log(options);



  getYVal = function(x, alpha, temp) {
    var result = Math.pow((1-x), 2); // (1-x)^2
    result *= alpha; // alpha * (1-x)^2
    return Math.pow(Math.E, result) * x; // x * exp(alpha * (1-x)^2)
  }

  $scope.setTemp = function(temp_val) {
    $scope.curr_temp = temp_val;
    console.log("Graph val: " + $scope.curr_temp);
    for (var i = 0; i < myChart.data.series[0].data.length; ++i) {
      var x = parseFloat(myChart.data.labels[i]);
      var alpha = curr_alphaP / (k * temp_val); // get alpha using current temp and alpha prime
      myChart.data.series[0].data[i] = getYVal(x, alpha, temp_val);
    }
    myChart.update(myChart.data);
  }

  $scope.setAlpha = function(alpha_val) {
    $scope.curr_alpha = alpha_val;
    console.log("Graph val: " + $scope.curr_alpha);
    // console.log(myChart);
    for (var i = 0; i < myChart.data.series[0].data.length; ++i) {
      var x = parseFloat(myChart.data.labels[i]);
      curr_alphaP = (alpha_val * k) * $scope.curr_temp; // get alpha prime using current temp and alpha values
      myChart.data.series[0].data[i] = getYVal(x, alpha_val, $scope.curr_temp);
      // myChart.data.series[0][i] = parseFloat(myChart.data.labels[i]) / parseInt(value);
    }
    myChart.update(myChart.data);
  }

  $scope.typeTemp = function() {
    var temp = $scope.text_input;
    if (temp >= 100 && temp <= 600) {
      $scope.curr_size = temp;
      $scope.setTemp(temp);
    }
    else {
      alert("please enter a valid temperature");
    }
  }

}]);















