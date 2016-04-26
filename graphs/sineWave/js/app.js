k = 8.31

var app = angular.module('graphApp',[]);


app.controller('sineCtrl', ['$scope', function($scope) {

  console.log("sine controller working");
  $scope.sine_input = 20;
  var time = 0;
    
  function OnDraw(value) {
    time = time + .1; // speeds up how quick it moves
    var canvas = document.getElementById("mycanvas");
    var dataLine = canvas.getContext("2d");
    
    dataLine.clearRect(0, 0, canvas.width, canvas.height);
    
    dataLine.beginPath();
    // <!-- dataLine.moveTo(0, canvas.height * 0.5); -->

    // var amplitude = 20;
    var amplitude = value;
    var phase = 0.05;

    for(cnt = -1; cnt <= canvas.width; cnt++) {
      dataLine.lineTo(cnt, canvas.height * 0.5 - (Math.random() * 2 + Math.cos(time + cnt * phase) * amplitude ));
    }
    dataLine.stroke();
  }

  drawInterval = setInterval(OnDraw, 100, 20);

  $scope.setCurve = function(value) {
    clearInterval(drawInterval);
    drawInterval = setInterval(OnDraw, 100, value)
  }




}]);













