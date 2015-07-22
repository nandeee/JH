'use strict';

angular.module('jhipsterApp').controller('Test3DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test3',
        function($scope, $stateParams, $modalInstance, entity, Test3) {

        $scope.test3 = entity;
        $scope.load = function(id) {
            Test3.get({id : id}, function(result) {
                $scope.test3 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test3Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test3.id != null) {
                Test3.update($scope.test3, onSaveFinished);
            } else {
                Test3.save($scope.test3, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
