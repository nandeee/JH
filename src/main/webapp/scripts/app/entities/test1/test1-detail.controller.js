'use strict';

angular.module('jhipsterApp')
    .controller('Test1DetailController', function ($scope, $rootScope, $stateParams, entity, Test1) {
        $scope.test1 = entity;
        $scope.load = function (id) {
            Test1.get({id: id}, function(result) {
                $scope.test1 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test1Update', function(event, result) {
            $scope.test1 = result;
        });
    });
