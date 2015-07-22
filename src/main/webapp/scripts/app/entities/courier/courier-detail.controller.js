'use strict';

angular.module('jhipsterApp')
    .controller('CourierDetailController', function ($scope, $rootScope, $stateParams, entity, Courier) {
        $scope.courier = entity;
        $scope.load = function (id) {
            Courier.get({id: id}, function(result) {
                $scope.courier = result;
            });
        };
        $rootScope.$on('jhipsterApp:courierUpdate', function(event, result) {
            $scope.courier = result;
        });
    });
