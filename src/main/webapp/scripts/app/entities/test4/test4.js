'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test4', {
                parent: 'entity',
                url: '/test4s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test4.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test4/test4s.html',
                        controller: 'Test4Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test4');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test4.detail', {
                parent: 'entity',
                url: '/test4/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test4.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test4/test4-detail.html',
                        controller: 'Test4DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test4');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test4', function($stateParams, Test4) {
                        return Test4.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test4.new', {
                parent: 'test4',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test4/test4-dialog.html',
                        controller: 'Test4DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {one: null, two: null, three: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test4', null, { reload: true });
                    }, function() {
                        $state.go('test4');
                    })
                }]
            })
            .state('test4.edit', {
                parent: 'test4',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test4/test4-dialog.html',
                        controller: 'Test4DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test4', function(Test4) {
                                return Test4.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test4', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
