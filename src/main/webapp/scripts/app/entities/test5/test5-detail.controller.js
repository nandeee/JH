'use strict';

angular.module('jhipsterApp')
    .controller('Test5DetailController', function ($scope, $rootScope, $stateParams, entity, Test5) {
        $scope.test5 = entity;
        $scope.load = function (id) {
            Test5.get({id: id}, function(result) {
                $scope.test5 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test5Update', function(event, result) {
            $scope.test5 = result;
        });
    });
