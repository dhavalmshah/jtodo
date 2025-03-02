import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, byteSize, openFile } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-attributes.reducer';

export const UserAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userAttributesEntity = useAppSelector(state => state.userAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAttributesDetailsHeading">User Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userAttributesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userAttributesEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{userAttributesEntity.email}</dd>
          <dt>
            <span id="emailVerified">Email Verified</span>
          </dt>
          <dd>
            {userAttributesEntity.emailVerified ? (
              <TextFormat value={userAttributesEntity.emailVerified} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {userAttributesEntity.image ? (
              <div>
                {userAttributesEntity.imageContentType ? (
                  <a onClick={openFile(userAttributesEntity.imageContentType, userAttributesEntity.image)}>
                    <img
                      src={`data:${userAttributesEntity.imageContentType};base64,${userAttributesEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {userAttributesEntity.imageContentType}, {byteSize(userAttributesEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userAttributesEntity.createdAt ? (
              <TextFormat value={userAttributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userAttributesEntity.updatedAt ? (
              <TextFormat value={userAttributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{userAttributesEntity.password}</dd>
          <dt>
            <span id="level">Level</span>
          </dt>
          <dd>{userAttributesEntity.level}</dd>
          <dt>
            <span id="points">Points</span>
          </dt>
          <dd>{userAttributesEntity.points}</dd>
          <dt>User</dt>
          <dd>{userAttributesEntity.user ? userAttributesEntity.user.id : ''}</dd>
          <dt>Assigned Todos</dt>
          <dd>
            {userAttributesEntity.assignedTodos
              ? userAttributesEntity.assignedTodos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {userAttributesEntity.assignedTodos && i === userAttributesEntity.assignedTodos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Badges</dt>
          <dd>
            {userAttributesEntity.badges
              ? userAttributesEntity.badges.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {userAttributesEntity.badges && i === userAttributesEntity.badges.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Project Members</dt>
          <dd>
            {userAttributesEntity.projectMembers
              ? userAttributesEntity.projectMembers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {userAttributesEntity.projectMembers && i === userAttributesEntity.projectMembers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-attributes/${userAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAttributesDetail;
