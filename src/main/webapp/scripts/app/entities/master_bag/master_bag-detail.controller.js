'use strict';

angular.module('jhipsterApp')
    .controller('Master_bagDetailController', function ($scope, $rootScope, $stateParams, entity, Master_bag, Courier) {
        $scope.master_bag = entity;
        $scope.load = function (id) {
            Master_bag.get({id: id}, function(result) {
                $scope.master_bag = result;
            });
        };
        $rootScope.$on('jhipsterApp:master_bagUpdate', function(event, result) {
            $scope.master_bag = result;
        });
    });
