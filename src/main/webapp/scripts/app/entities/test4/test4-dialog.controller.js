'use strict';

angular.module('jhipsterApp').controller('Test4DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test4',
        function($scope, $stateParams, $modalInstance, entity, Test4) {

        $scope.test4 = entity;
        $scope.load = function(id) {
            Test4.get({id : id}, function(result) {
                $scope.test4 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test4Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test4.id != null) {
                Test4.update($scope.test4, onSaveFinished);
            } else {
                Test4.save($scope.test4, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
