import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tcr.reducer';
import { ITcr } from 'app/shared/model/tcr.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITcrDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TcrDetail extends React.Component<ITcrDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tcrEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Tcr [<b>{tcrEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tcrHash">Tcr Hash</span>
            </dt>
            <dd>{tcrEntity.tcrHash}</dd>
            <dt>
              <span id="tcrName">Tcr Name</span>
            </dt>
            <dd>{tcrEntity.tcrName}</dd>
            <dt>
              <span id="content">Content</span>
            </dt>
            <dd>{tcrEntity.content}</dd>
            <dt>
              <span id="bondingCurve">Bonding Curve</span>
            </dt>
            <dd>{tcrEntity.bondingCurve}</dd>
          </dl>
          <Button tag={Link} to="/entity/tcr" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/tcr/${tcrEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tcr }: IRootState) => ({
  tcrEntity: tcr.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TcrDetail);
