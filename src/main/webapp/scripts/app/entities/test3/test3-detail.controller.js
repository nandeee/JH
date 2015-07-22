'use strict';

angular.module('jhipsterApp')
    .controller('Test3DetailController', function ($scope, $rootScope, $stateParams, entity, Test3) {
        $scope.test3 = entity;
        $scope.load = function (id) {
            Test3.get({id: id}, function(result) {
                $scope.test3 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test3Update', function(event, result) {
            $scope.test3 = result;
        });
    });
