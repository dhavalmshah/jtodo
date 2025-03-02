import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './todo.reducer';

export const Todo = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const todoList = useAppSelector(state => state.todo.entities);
  const loading = useAppSelector(state => state.todo.loading);
  const links = useAppSelector(state => state.todo.links);
  const updateSuccess = useAppSelector(state => state.todo.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="todo-heading" data-cy="TodoHeading">
        Todos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/todo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Todo
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={todoList ? todoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {todoList && todoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('title')}>
                    Title <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    Description <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('dueDate')}>
                    Due Date <FontAwesomeIcon icon={getSortIconByFieldName('dueDate')} />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    Created At <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    Updated At <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('priority')}>
                    Priority <FontAwesomeIcon icon={getSortIconByFieldName('priority')} />
                  </th>
                  <th className="hand" onClick={sort('pointsAwarded')}>
                    Points Awarded <FontAwesomeIcon icon={getSortIconByFieldName('pointsAwarded')} />
                  </th>
                  <th>
                    Creator <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Project <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Parent <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {todoList.map((todo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/todo/${todo.id}`} color="link" size="sm">
                        {todo.id}
                      </Button>
                    </td>
                    <td>{todo.title}</td>
                    <td>{todo.description}</td>
                    <td>{todo.dueDate ? <TextFormat type="date" value={todo.dueDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{todo.createdAt ? <TextFormat type="date" value={todo.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{todo.updatedAt ? <TextFormat type="date" value={todo.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{todo.status}</td>
                    <td>{todo.priority}</td>
                    <td>{todo.pointsAwarded}</td>
                    <td>{todo.creator ? <Link to={`/user-attributes/${todo.creator.id}`}>{todo.creator.id}</Link> : ''}</td>
                    <td>{todo.project ? <Link to={`/project/${todo.project.id}`}>{todo.project.id}</Link> : ''}</td>
                    <td>{todo.parent ? <Link to={`/todo/${todo.parent.id}`}>{todo.parent.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/todo/${todo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`/todo/${todo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/todo/${todo.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Todos found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Todo;
