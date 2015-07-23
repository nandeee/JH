'use strict';

angular.module('jhipsterApp').controller('Test6DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Test6',
        function($scope, $stateParams, $modalInstance, entity, Test6) {

        $scope.test6 = entity;
        $scope.load = function(id) {
            Test6.get({id : id}, function(result) {
                $scope.test6 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:test6Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.test6.id != null) {
                Test6.update($scope.test6, onSaveFinished);
            } else {
                Test6.save($scope.test6, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
