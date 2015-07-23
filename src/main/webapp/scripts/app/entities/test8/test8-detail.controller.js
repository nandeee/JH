'use strict';

angular.module('jhipsterApp')
    .controller('Test8DetailController', function ($scope, $rootScope, $stateParams, entity, Test8) {
        $scope.test8 = entity;
        $scope.load = function (id) {
            Test8.get({id: id}, function(result) {
                $scope.test8 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test8Update', function(event, result) {
            $scope.test8 = result;
        });
    });
