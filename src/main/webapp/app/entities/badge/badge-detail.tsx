import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './badge.reducer';

export const BadgeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const badgeEntity = useAppSelector(state => state.badge.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="badgeDetailsHeading">Badge</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{badgeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{badgeEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{badgeEntity.description}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>{badgeEntity.image}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{badgeEntity.createdAt ? <TextFormat value={badgeEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{badgeEntity.updatedAt ? <TextFormat value={badgeEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{badgeEntity.type}</dd>
          <dt>
            <span id="criteria">Criteria</span>
          </dt>
          <dd>{badgeEntity.criteria}</dd>
          <dt>Users</dt>
          <dd>
            {badgeEntity.users
              ? badgeEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {badgeEntity.users && i === badgeEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/badge" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/badge/${badgeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BadgeDetail;
