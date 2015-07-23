'use strict';

angular.module('jhipsterApp')
    .controller('Test9DetailController', function ($scope, $rootScope, $stateParams, entity, Test9) {
        $scope.test9 = entity;
        $scope.load = function (id) {
            Test9.get({id: id}, function(result) {
                $scope.test9 = result;
            });
        };
        $rootScope.$on('jhipsterApp:test9Update', function(event, result) {
            $scope.test9 = result;
        });
    });
