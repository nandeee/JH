'use strict';

angular.module('jhipsterApp')
    .controller('Test2DetailController', function ($scope, $rootScope, $stateParams, entity, Test2) {
        $scope.test2 = entity;
        $scope.load = function (id) {
            Test2.get({id: id}, function(result) {
                $scope.test2 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test2Update', function(event, result) {
            $scope.test2 = result;
        });
    });
