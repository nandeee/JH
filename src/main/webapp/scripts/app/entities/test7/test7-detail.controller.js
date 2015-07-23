'use strict';

angular.module('jhipsterApp')
    .controller('Test7DetailController', function ($scope, $rootScope, $stateParams, entity, Test7) {
        $scope.test7 = entity;
        $scope.load = function (id) {
            Test7.get({id: id}, function(result) {
                $scope.test7 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test7Update', function(event, result) {
            $scope.test7 = result;
        });
    });
