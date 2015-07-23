'use strict';

angular.module('jhipsterApp').controller('Test7DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test7',
        function($scope, $stateParams, $modalInstance, entity, Test7) {

        $scope.test7 = entity;
        $scope.load = function(id) {
            Test7.get({id : id}, function(result) {
                $scope.test7 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test7Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test7.id != null) {
                Test7.update($scope.test7, onSaveFinished);
            } else {
                Test7.save($scope.test7, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
