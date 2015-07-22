'use strict';

angular.module('jhipsterApp').controller('AuthorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Author',
        function($scope, $stateParams, $modalInstance, entity, Author) {

        $scope.author = entity;
        $scope.load = function(id) {
            Author.get({id : id}, function(result) {
                $scope.author = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:authorUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.author.id != null) {
                Author.update($scope.author, onSaveFinished);
            } else {
                Author.save($scope.author, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
