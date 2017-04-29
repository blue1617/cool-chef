'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myApp.view1',
  'myApp.view2',
  'myApp.version'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/view1'});
}]);


angular.module('xmpl.service', [])

    .value('greeter', {
        salutation: 'Hello',
        localize: function(localization) {
            this.salutation = localization.salutation;
        },
        greet: function(name) {
            return this.salutation + ' ' + name + '!';
        }
    })

    .value('user', {
        load: function(name) {
            this.name = name;
        }
    });

angular.module('xmpl.directive', []);

angular.module('xmpl.filter', []);

angular.module('xmpl', ['xmpl.service', 'xmpl.directive', 'xmpl.filter'])

    .run(function(greeter, user) {
        // This is effectively part of the main method initialization code
        greeter.localize({
            salutation: 'Bonjour'
        });
        user.load('World');
    })

    .controller('XmplController', function($scope, greeter, user) {
        $scope.greeting = greeter.greet(user.name);
    });
