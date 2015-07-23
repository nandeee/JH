'use strict';

angular.module('jhipsterApp')
    .controller('Test6DetailController', function ($scope, $rootScope, $stateParams, entity, Test6) {
        $scope.test6 = entity;
        $scope.load = function (id) {
            Test6.get({id: id}, function(result) {
                $scope.test6 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test6Update', function(event, result) {
            $scope.test6 = result;
        });
    });
