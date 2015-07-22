'use strict';

angular.module('jhipsterApp').controller('Test1DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test1',
        function($scope, $stateParams, $modalInstance, entity, Test1) {

        $scope.test1 = entity;
        $scope.load = function(id) {
            Test1.get({id : id}, function(result) {
                $scope.test1 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test1Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test1.id != null) {
                Test1.update($scope.test1, onSaveFinished);
            } else {
                Test1.save($scope.test1, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
