'use strict';

angular.module('jhipsterApp').controller('Master_bagDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Master_bag', 'Courier',
        function($scope, $stateParams, $modalInstance, entity, Master_bag, Courier) {

        $scope.master_bag = entity;
        $scope.couriers = Courier.query();
        $scope.load = function(id) {
            Master_bag.get({id : id}, function(result) {
                $scope.master_bag = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:master_bagUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.master_bag.id != null) {
                Master_bag.update($scope.master_bag, onSaveFinished);
            } else {
                Master_bag.save($scope.master_bag, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
