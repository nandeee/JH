'use strict';

angular.module('jhipsterApp')
    .controller('Test5Controller', function ($scope, Test5) {
        window.scope = $scope;
        $scope.test5s = [];
        $scope.loadAll = function() {
            Test5.query(function(result) {
               $scope.test5s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test5.get({id: id}, function(result) {
                $scope.test5 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test5.id != null) {
                Test5.update($scope.test5,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test5.save($scope.test5,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test5 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test5.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test5 = {name: null, age: null, date: null, id: null};

        $scope.clear = function () {
            $scope.test5 = {name: null, age: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test5s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test5.update(rowEntity,
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
                field: "name",
                displayName: "name"

            }, {
                field: "age",
                displayName: "age"

            }, {
                field: "date",
                displayName: "date"


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
