'use strict';

angular.module('jhipsterApp')
    .controller('Test2Controller', function ($scope, Test2) {
        window.scope = $scope;
        $scope.test2s = [];
        $scope.loadAll = function() {
            Test2.query(function(result) {
               $scope.test2s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test2.get({id: id}, function(result) {
                $scope.test2 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test2.id != null) {
                Test2.update($scope.test2,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test2.save($scope.test2,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test2 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test2.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test2 = {age: null, id: null};

        $scope.clear = function () {
            $scope.test2 = {age: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test2s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test2.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "age",
                displayName: "age"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                headerCellTemplate: '<span></span>',
                cellClass: 'tableButton',
                cellTemplate: '<div class="tableButton"><button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button></div>'
            }],
        };

        $scope.ctrl = {};
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query, entity) {
            var results = query ? $scope[entity].filter(createFilterFor(query)) : $scope[entity];
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.toLowerCase().indexOf(lowercaseQuery) === 0);
            };
        }
    });
