'use strict';

angular.module('jhipsterApp').controller('Test8DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test8',
        function($scope, $stateParams, $modalInstance, entity, Test8) {

        $scope.test8 = entity;
        $scope.load = function(id) {
            Test8.get({id : id}, function(result) {
                $scope.test8 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test8Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test8.id != null) {
                Test8.update($scope.test8, onSaveFinished);
            } else {
                Test8.save($scope.test8, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
