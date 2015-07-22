'use strict';

angular.module('jhipsterApp').controller('Test5DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test5',
        function($scope, $stateParams, $modalInstance, entity, Test5) {

        $scope.test5 = entity;
        $scope.load = function(id) {
            Test5.get({id : id}, function(result) {
                $scope.test5 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test5Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test5.id != null) {
                Test5.update($scope.test5, onSaveFinished);
            } else {
                Test5.save($scope.test5, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
