import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attachment.reducer';

export const AttachmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">Attachment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{attachmentEntity.url}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{attachmentEntity.type}</dd>
          <dt>
            <span id="size">Size</span>
          </dt>
          <dd>{attachmentEntity.size}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {attachmentEntity.createdAt ? <TextFormat value={attachmentEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {attachmentEntity.updatedAt ? <TextFormat value={attachmentEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{attachmentEntity.name}</dd>
          <dt>Uploader</dt>
          <dd>{attachmentEntity.uploader ? attachmentEntity.uploader.id : ''}</dd>
          <dt>Todo</dt>
          <dd>{attachmentEntity.todo ? attachmentEntity.todo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttachmentDetail;
