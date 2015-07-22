'use strict';

angular.module('jhipsterApp')
    .controller('Test4DetailController', function ($scope, $rootScope, $stateParams, entity, Test4) {
        $scope.test4 = entity;
        $scope.load = function (id) {
            Test4.get({id: id}, function(result) {
                $scope.test4 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test4Update', function(event, result) {
            $scope.test4 = result;
        });
    });
