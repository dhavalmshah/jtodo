import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, byteSize, getSortState, openFile } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './user-attributes.reducer';

export const UserAttributes = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const userAttributesList = useAppSelector(state => state.userAttributes.entities);
  const loading = useAppSelector(state => state.userAttributes.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="user-attributes-heading" data-cy="UserAttributesHeading">
        User Attributes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/user-attributes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new User Attributes
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userAttributesList && userAttributesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  Email <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('emailVerified')}>
                  Email Verified <FontAwesomeIcon icon={getSortIconByFieldName('emailVerified')} />
                </th>
                <th className="hand" onClick={sort('image')}>
                  Image <FontAwesomeIcon icon={getSortIconByFieldName('image')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  Updated At <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('password')}>
                  Password <FontAwesomeIcon icon={getSortIconByFieldName('password')} />
                </th>
                <th className="hand" onClick={sort('level')}>
                  Level <FontAwesomeIcon icon={getSortIconByFieldName('level')} />
                </th>
                <th className="hand" onClick={sort('points')}>
                  Points <FontAwesomeIcon icon={getSortIconByFieldName('points')} />
                </th>
                <th>
                  User <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Assigned Todos <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Badges <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Project Members <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userAttributesList.map((userAttributes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-attributes/${userAttributes.id}`} color="link" size="sm">
                      {userAttributes.id}
                    </Button>
                  </td>
                  <td>{userAttributes.name}</td>
                  <td>{userAttributes.email}</td>
                  <td>
                    {userAttributes.emailVerified ? (
                      <TextFormat type="date" value={userAttributes.emailVerified} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userAttributes.image ? (
                      <div>
                        {userAttributes.imageContentType ? (
                          <a onClick={openFile(userAttributes.imageContentType, userAttributes.image)}>
                            <img
                              src={`data:${userAttributes.imageContentType};base64,${userAttributes.image}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {userAttributes.imageContentType}, {byteSize(userAttributes.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {userAttributes.createdAt ? <TextFormat type="date" value={userAttributes.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {userAttributes.updatedAt ? <TextFormat type="date" value={userAttributes.updatedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userAttributes.password}</td>
                  <td>{userAttributes.level}</td>
                  <td>{userAttributes.points}</td>
                  <td>{userAttributes.user ? userAttributes.user.id : ''}</td>
                  <td>
                    {userAttributes.assignedTodos
                      ? userAttributes.assignedTodos.map((val, j) => (
                          <span key={j}>
                            <Link to={`/todo/${val.id}`}>{val.id}</Link>
                            {j === userAttributes.assignedTodos.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {userAttributes.badges
                      ? userAttributes.badges.map((val, j) => (
                          <span key={j}>
                            <Link to={`/badge/${val.id}`}>{val.id}</Link>
                            {j === userAttributes.badges.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {userAttributes.projectMembers
                      ? userAttributes.projectMembers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/project/${val.id}`}>{val.id}</Link>
                            {j === userAttributes.projectMembers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/user-attributes/${userAttributes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-attributes/${userAttributes.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/user-attributes/${userAttributes.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No User Attributes found</div>
        )}
      </div>
    </div>
  );
};

export default UserAttributes;
