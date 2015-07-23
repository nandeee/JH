'use strict';

angular.module('jhipsterApp').controller('Test9DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test9',
        function($scope, $stateParams, $modalInstance, entity, Test9) {

        $scope.test9 = entity;
        $scope.load = function(id) {
            Test9.get({id : id}, function(result) {
                $scope.test9 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test9Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test9.id != null) {
                Test9.update($scope.test9, onSaveFinished);
            } else {
                Test9.save($scope.test9, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
