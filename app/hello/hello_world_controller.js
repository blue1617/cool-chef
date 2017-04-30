/**
 * Created by apreda on 29.04.2017.
 */

angular.module('HelloWorldApp', [])
    .controller('HelloWorldController', function ($scope, $http) {
        $scope.greeting = "Hello World";
        $scope.tasks = [];
        $scope.add = function () {
            $scope.tasks.push($scope.title);
        }
        $scope.delete = function () {
            $scope.tasks.splice(this.$index, 1);
        }
        // Simple GET request example:
        // $http({
        //     method: 'GET',
        //     url: 'http://www.google.com'
        // }).then(function successCallback(response) {
        //     // this callback will be called asynchronously
        //     // when the response is available
        //     console.log("adadas");
        // }, function errorCallback(response) {
        //     // called asynchronously if an error occurs
        //     // or server returns response with an error status.
        // });
    });