'use strict';

angular.module('jhipsterApp')
    .controller('Test7Controller', function ($scope, Test7) {
        window.scope = $scope;
        $scope.test7s = [];
        $scope.loadAll = function() {
            Test7.get(function(result) {
               $scope.test7s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test7.get({id: id}, function(result) {
                $scope.test7 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test7.id != null) {
                Test7.update($scope.test7,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test7.save($scope.test7,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test7 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test7.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test7 = {name: null, age: null, date: null, etad: null, id: null};

        $scope.clear = function () {
            $scope.test7 = {name: null, age: null, date: null, etad: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test7s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test7.update(rowEntity,
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
                displayName: "date",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="datetime-local" ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'


            }, {
                field: "etad",
                displayName: "etad",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="date" ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'


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
