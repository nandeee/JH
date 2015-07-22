'use strict';

angular.module('jhipsterApp').controller('Test2DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test2',
        function($scope, $stateParams, $modalInstance, entity, Test2) {

        $scope.test2 = entity;
        $scope.load = function(id) {
            Test2.get({id : id}, function(result) {
                $scope.test2 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test2Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test2.id != null) {
                Test2.update($scope.test2, onSaveFinished);
            } else {
                Test2.save($scope.test2, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
