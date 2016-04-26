
var app = angular.module('boxApp',[]);

app.controller('boxCtrl', ['$scope', function($scope) {
	widget.notifyContentIsReady()
	
	console.log("App controller working!");

	$scope.current_size = 100;

	$scope.setBoxSize = function(value) {
		$scope.current_size = value;
		console.log("Size of Box: " + $scope.current_size);
	}


}]);