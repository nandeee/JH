'use strict';

angular.module('jhipsterApp').controller('CourierDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Courier',
        function($scope, $stateParams, $modalInstance, entity, Courier) {

        $scope.courier = entity;
        $scope.load = function(id) {
            Courier.get({id : id}, function(result) {
                $scope.courier = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:courierUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.courier.id != null) {
                Courier.update($scope.courier, onSaveFinished);
            } else {
                Courier.save($scope.courier, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
