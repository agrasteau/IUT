import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import { Button, Col, Flex, Layout, Modal, Row, Select, Space, Tabs, Tooltip, Typography, message } from 'antd';

import { AntDesignOutlined, AppstoreOutlined, DeleteOutlined, FolderOutlined, PlusOutlined, UnorderedListOutlined } from '@ant-design/icons';

import '../../assets/css/pages/bibliotheque.scss';
import '../../assets/css/pages/cards.scss';
import '../../assets/css/pages/filtres.scss';
import '../../assets/css/pages/listing.scss';

/** @jsxImportSource @emotion/react */
// import { css } from '@emotion/react'; 
import useInstall from '../../hooks/useInstall';
import useStore from '../../hooks/useStore';
import PageHeader from '../navigation/PageHeader';
//import GirdCenter from '../../components/GridCenter';
import { useTranslation } from 'react-i18next';
import { TRACES } from '../../db/traces';
import { ActivityDocType, RxColOp } from '../../db/types';
import { ActivityType } from '../../features/activity/ActivityType';
import { useActivity } from '../../hooks';
import { useTrace } from '../../hooks/useTrace';
import { getID } from '../../utils/uniqueId';
import { ActivityCard } from '../activity/ActivityCard';
import ActivityDescription from '../activity/ActivityDescription';
import ActivityFilters, { init } from "../activity/ActivityFilters";
import { activityTypeList } from '../activity/ActivityType';
import GroupDescription from '../activity/GroupDescription';
import PathDescription from '../activity/PathDescription';


const IconLink = ({
  icon,
  text,
  onClick,
}: {
  icon: any;
  text: string;
  onClick?: () => void;
}) => (
  <Button
    onClick={onClick}
    css={{
      display: 'flex',
    }}
    type='text'
    size='large'>
    <Button
      css={{
        color: 'var(--primary-color)',
        marginRight: 4,
      }}
      type='default'
      icon={icon}
      shape='circle'
    />
    <Typography.Link style={{ fontSize: 18 }}>{text}</Typography.Link>
  </Button>
);

const HeaderContent = ({ onInstall, showInstall }: any) => {
  const { t } = useTranslation();
  return (
    <Space
      direction='vertical'
      css={{
        width: '100%',
        display: 'flex',
        flexWrap: 'wrap',
        margin: 'auto',
      }}>
      <Typography.Text css={{ fontSize: '1rem' }}>
        <Typography.Text strong>MIXAP</Typography.Text>{' '}
        {t('common.description-part1')}{' '}
        <Typography.Link
          rel='noopener noreferrer'
          href='https://lium.univ-lemans.fr/'
          target='_blank'
          strong>
          {'LIUM'}
        </Typography.Link>
        {t('common.description-part2')}{' '}
        <Typography.Text strong>
          {t('common.augmented-reality')}
        </Typography.Text>
        {t('common.description-part3')}{' '}
        <Typography.Text strong>{t('common.teachers')}</Typography.Text>.{' '}
        {t('common.description-part4')}
        <br></br>
        {t('common.description-part5')}{' '}
        <Typography.Link
          rel='noopener noreferrer'
          href='https://mixap-lium.univ-lemans.fr/'
          target='_blank'
          strong>
          {'MIXAP'}
        </Typography.Link>
        {t('common.description-part6')}
      </Typography.Text>
    </Space>
  );
};

export default function Home() {
  const activities = useStore((state) => state.activitySlice.activities);
  const showMenu = useStore((state) => state.activitySlice.showMenu);
  const { trace } = useTrace({});
  const { onInstall, shoudldInstall } = useInstall();
  const navigate = useNavigate();
  const { Option } = Select;



  const locationNavigate = useLocation();

  
  const ancreTab = locationNavigate.state?.ancre;

  const { Content } = Layout;

  //Permet de savoir sur quelle Tab ce mettre
  const [activeKey, setActiveKey] = React.useState(ancreTab === 'group' || ancreTab === 'path' ? ancreTab : 'activity')
  
  //Change de Tab pour pouvoir revenir sur celle sélectionnée
  const onKeyChange = (key) => {
    if(locationNavigate.state !== null){
      locationNavigate.state.ancre = key;
    } else {
      locationNavigate.state = {'ancre':key};
    }
    setActiveKey(key);
    
  }
  const deleteActivityById = (activityIdToDelete) => {
    // Find the activity to delete
    const activityToDelete = activities.find(
      (activity) => activity.id === activityIdToDelete
    );
  
    if (!activityToDelete) {
      console.error(`Activity with ID ${activityIdToDelete} not found.`);
      return;
    }
  
    // Remove the activity
    onRxColActivity(RxColOp.Remove, { id: activityIdToDelete });
  
    // Handle activity types with references (Group, Path, SmartGroup)
    if (
      activityToDelete.type === ActivityType.Group ||
      activityToDelete.type === ActivityType.Path ||
      activityToDelete.type === ActivityType.SmartGroup
    ) {
      // Remove references from child activities
      if (
        activityToDelete.comboIds?.length !== undefined &&
        activityToDelete.comboIds?.length > 0
      ) {
        activityToDelete.comboIds.forEach((childActivityId) => {
          const childActivity = activities.find(
            (act) => act.id === childActivityId
          );
  
          if (childActivity) {
            if (
              activityToDelete.type === ActivityType.Group ||
              activityToDelete.type === ActivityType.SmartGroup
            ) {
              const updatedGroup = childActivity.group?.filter(
                (item) => item !== activityIdToDelete
              );
              onRxColActivity(RxColOp.Update, {
                id: childActivityId,
                group: updatedGroup,
              });
            }
  
            if (activityToDelete.type === ActivityType.Path) {
              const updatedPath = childActivity.path?.filter(
                (item) => item !== activityIdToDelete
              );
              onRxColActivity(RxColOp.Update, {
                id: childActivityId,
                path: updatedPath,
              });
            }
          }
        });
      }
    } else {
      // Update group and path references for regular activities
      if (activityToDelete.group?.length > 0) {
        activityToDelete.group.forEach((groupId) => {
          const groupActivity = activities.find(
            (a) =>
              a.id === groupId &&
              (a.type === ActivityType.Group || a.type === ActivityType.SmartGroup)
          );
  
          if (groupActivity?.comboIds?.length > 0) {
            const updatedComboIds = groupActivity.comboIds.filter(
              (id) => id !== activityIdToDelete
            );
            onRxColActivity(RxColOp.Update, {
              id: groupActivity.id,
              comboIds: updatedComboIds,
            });
          }
        });
      }
  
      if (activityToDelete.path?.length > 0) {
        activityToDelete.path.forEach((pathId) => {
          const pathActivity = activities.find(
            (a) => a.id === pathId && a.type === ActivityType.Path
          );
  
          if (pathActivity?.comboIds?.length > 0) {
            const updatedComboIds = pathActivity.comboIds.filter(
              (id) => id !== activityIdToDelete
            );
            onRxColActivity(RxColOp.Update, {
              id: pathActivity.id,
              comboIds: updatedComboIds,
            });
          }
        });
      }
    }
  
    console.log(`Activity with ID ${activityIdToDelete} has been deleted.`);
  };
  
  
  const DeleteGroupActivities = ({ groups, onDeleteGroupActivities }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedGroup, setSelectedGroup] = useState(null);

    const showModal = () => {
        setIsModalOpen(true);
    };

    const handleOk = () => {
        if (selectedGroup) {
            const group = groups.find(group => group.id === selectedGroup);
            console.log(group)
         
            if (group && group.comboIds && group.comboIds.length > 0) {
              group.comboIds.forEach((activityId) => {
                deleteActivityById(activityId);
                console.log(`Deleted activity with ID: ${activityId}`);
              });
              deleteActivityById(group.id);

            } else {
                message.info('No activities found in the selected group.');
            }
          
        } else {
            message.warning('Please select a group.');
        }

        setIsModalOpen(false);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    return (
        <>
        
            <Button       
            size="large"
            type="primary"
            shape="round"
            style={{fontWeight: 500,}}
            onClick={showModal}>
            <DeleteOutlined style={{ fontSize: 22, color: '#eee' }} />
                Delete Group Activities
            </Button>
            <Modal
                title="Select a Group to Delete Activities"
                visible={isModalOpen}
                onOk={handleOk}
                onCancel={handleCancel}
            >
                <Select
                    style={{ width: '100%' }}
                    placeholder="Select a group"
                    onChange={(value) => setSelectedGroup(value)}
                >
                    {groups.map((group) => (
                        <Select.Option key={group.id} value={group.id}>
                            {group.title}
                        </Select.Option>
                    ))}
                </Select>
            </Modal>
        </>
    );
};


  const { onRxColActivity } = useActivity();
  const handleDeleteAll = () => {
  // Loop through all activities and process each one
  activities.forEach((activity) => {
    const activityId = activity.id;

    // Remove the activity
    onRxColActivity(RxColOp.Remove, { id: activityId });

    if (
      activity?.type === ActivityType.Group ||
      activity?.type === ActivityType.Path ||
      activity?.type === ActivityType.SmartGroup
    ) {
      // Remove activity references in comboIds
      if (
        activity.comboIds?.length !== undefined &&
        activity.comboIds?.length > 0
      ) {
        activity.comboIds.forEach((idChildActivity) => {
          const childActivity = activities.find(
            (act) => act.id === idChildActivity
          );

          if (childActivity) {
            if (
              activity.type === ActivityType.Group ||
              activity.type === ActivityType.SmartGroup
            ) {
              const updatedGroup = childActivity?.group?.filter(
                (item) => item !== activityId
              );
              onRxColActivity(RxColOp.Update, {
                id: idChildActivity,
                group: updatedGroup,
              });
            }

            if (activity.type === ActivityType.Path) {
              const updatedPath = childActivity?.group?.filter(
                (item) => item !== activityId
              );
              onRxColActivity(RxColOp.Update, {
                id: idChildActivity,
                path: updatedPath,
              });
            }
          }
        });
      }
    } else {
      // Update group or path references for regular activities
      let replaceComboIdsGroup = [];
      let replaceComboIdsPath = [];

      if (activity?.group?.length > 0) {
        activity.group.forEach((idGroup) => {
          const groupActivity = activities.find(
            (a) =>
              a.id === idGroup &&
              (a.type === ActivityType.Group ||
                a.type === ActivityType.SmartGroup)
          );

          if (groupActivity?.comboIds?.length > 0) {
            replaceComboIdsGroup = groupActivity.comboIds.filter(
              (actId) => actId !== activity.id
            );

            onRxColActivity(RxColOp.Update, {
              id: groupActivity.id,
              comboIds: replaceComboIdsGroup,
            });
          }
        });
      }

      if (activity?.path?.length > 0) {
        activity.path.forEach((idPath) => {
          const pathActivity = activities.find(
            (a) => a.id === idPath && a.type === ActivityType.Path
          );

          if (pathActivity?.comboIds?.length > 0) {
            replaceComboIdsPath = pathActivity.comboIds.filter(
              (actId) => actId !== activity.id
            );

            onRxColActivity(RxColOp.Update, {
              id: pathActivity.id,
              comboIds: replaceComboIdsPath,
            });
          }
        });
      }
    }
  });



  trace(TRACES.REMOVE_ALL_ACTIVITIES);
};

  const handlePlay = (activityId) => {
    onRxColActivity(RxColOp.SetCurrActivity, {
      id: activityId,
    });

    navigate(`/play-activity/${activityId}/${activeKey}`);
  };


  const handleAddActivity = () => {
    showMenu();
    trace(TRACES.ADD_ACTIVITY_CTA);
  };

  const user = useStore((state) => state.authSlice.user);

  //Permet de créer un groupe "d'activité" ou un "parcours" lors du clic sur le bouton
  const handleAddGroups = (type: string) => {
    showMenu();
    trace(TRACES.ADD_ACTIVITY_CTA);
  };

  const handleAddPath = (type: string) => {
    
    const id = getID();
    onRxColActivity(RxColOp.Add, {
      id,
      type: type,
      title: t(activityTypeList[type].placeholders.title) as string,
      instruction: t(
        activityTypeList[type].placeholders.instruction,
      ) as string,
      description: t(
        activityTypeList[type].placeholders.description,
      ) as string,
      userId: user?.id,
    });

    navigate(`/edit-activity/${id}/${activeKey}`);
    
    trace(TRACES.ADD_ACTIVITY_CTA);
  };


  const cards = activities.map((activity: ActivityDocType) => (
    
    <ActivityCard
      activity={activity}
      key={activity.id}
      onCardClick={handlePlay.bind(activity.id)}
      tabKey={activeKey}
    />
  ));


  const cardsActivities: any[] = [];
  if(cards.length > 0)
  {
    cards.forEach((item) => {
      if(item.props.activity.type === ActivityType.Validation ||
      item.props.activity.type === ActivityType.Augmenter ||
      item.props.activity.type === ActivityType.Augmentation ||
      item.props.activity.type === ActivityType.Augmentation ||
      item.props.activity.type === ActivityType.augmentationFromPhysical ||
      item.props.activity.type === ActivityType.Association
      ){
        cardsActivities.push(item);
      }
    });
  }

  const cardsGroups: any[] = [];
  if(cards.length > 0)
  {
    cards.forEach((item) => {
      if(item.props.activity.type === ActivityType.Group || item.props.activity.type === ActivityType.SmartGroup){
        cardsGroups.push(item);
      }
    });
  }

  const cardsPaths: any[] = [];
  if(cards.length > 0)
  {
    cards.forEach((item) => {
      if(item.props.activity.type === ActivityType.Path ){
        cardsPaths.push(item);
      }
    });
  }

  const { t } = useTranslation();



  const cards2listSwitcher = document.getElementById('switchList');
  cards2listSwitcher?.addEventListener('click', cards2list);

  function cards2list(e){
    e.stopPropagation();
    document.body.classList.add('-listing');
    noCardsTransition();
  }

  const list2cardsSwitcher = document.getElementById('switchCards');
  list2cardsSwitcher?.addEventListener('click', list2cards);

  function list2cards(e){
    e.stopPropagation();
    document.body.classList.remove('-listing');
    noCardsTransition();
  }

  function noCardsTransition(){
    document.body.classList.add('-noCardsTransition');
    
    
    setTimeout(() => {
      document.body.classList.remove('-noCardsTransition');
    }, 1000);
  }

  //Permet de rendre fonctionnel le web component ActivityFilters
  init(['-augmenter','-valider','-associer','-superposer','-exploiter','-augementationphysique'],
    ['#filtreAugmenter','#filtreValider','#filtreAssocier','#filtreSuperposer','#filtreExploiter','#filtrePhisicalAugement'],
    'resetFiltres'
  );


  return (
    // <Space
    //   direction='vertical'
    //   css={{ width: '100%', height: '100%', overflowY: 'auto' }}>
    <Layout className='mix-app'>   
      
      <PageHeader
        route='/'
        content={
          <HeaderContent
            onInstall={onInstall}
            showInstall={shoudldInstall}
          />
        }
      />
      <div className='mix-spacer' />
      <section className="mix-main" >
        
      
        <section className='mix-main_content'>

          <div id="switchList" className="mix-switcher">
            <Tooltip title={t('common.display-as-list')} placement="bottomRight">
              <span className="mix-switcher_bt -active"><AppstoreOutlined /></span>
              <span className="mix-switcher_bt"><UnorderedListOutlined /></span>
            </Tooltip>
          </div>
          <div id="switchCards" className="mix-switcher">
            <Tooltip title={t('common.display-as-cards')} placement="bottomRight">
              <span className="mix-switcher_bt"><AppstoreOutlined /></span>
              <span className="mix-switcher_bt -active"><UnorderedListOutlined /></span>
            </Tooltip>
          </div>

        
          
          <Tabs tabBarExtraContent={{ left : <Typography.Title level={4}>{t('common.my-library')}</Typography.Title> }} activeKey={activeKey} onChange={onKeyChange} items={
              [

                {
                  key: 'activity', 
                  label: <span>{t('common.activity')} <span className='mix-tab_qte'>{cardsActivities.length}</span></span>,
                  children:
                  <Content className='mix-appWrap -activities'>
                    <Row className='mix-main'>
                      <Col className="mix-main_aside">
                      <div className="mix-spacer" />
                      <ActivityDescription />
                      <div
                        style={{
                          display: 'flex',
                          flexDirection: 'column', // Align items vertically
                          justifyContent: 'center',
                          alignItems: 'center',
                          gap: '1rem', // Spacing between buttons
                        }}
                      >
                        <Button
                          size="large"
                          type="primary"
                          shape="round"
                          style={{
                            fontWeight: 500,
                          }}
                                icon={<PlusOutlined style={{ fontSize: 22, color: '#eee' }} />}
                                onClick={handleAddActivity}
                        >
                          {t('common.activity')}
                        </Button>
                        <Button
                          size="large"
                          type="primary"
                          shape="round"
                          style={{
                            fontWeight: 500,
                          }}
                          icon={<DeleteOutlined style={{ fontSize: 22, color: '#eee' }} />}
                                onClick={handleDeleteAll}
                        >
                          {t('common.drop-all-activities')}
                        </Button>
                      </div>
                    </Col>

                      <Col className='mix-main_content -activities'>
                        <div className='mix-spacer' />

                        <ActivityFilters/>

                        <div className='mix-spacer' />
                        <Flex wrap gap="middle" className='mix-main_content_item'>
                        {cardsActivities}
                        </Flex>

                      
                        
                      </Col>

                    </Row>
                  </Content>,

                  icon: <AntDesignOutlined/>
                },
                {
                  key: 'group',
                  label: <span>{t('common.group-placeholder-title')} <span className='mix-tab_qte'>{cardsGroups.length}</span></span>,
                  children:

                  <Content className='mix-appWrap'>
                    <Row className='mix-main'>
                      <Col className="mix-main_aside">
                        <div className="mix-spacer" />
                        <GroupDescription />
                        <div
                          style={{
                            display: 'flex',
                            flexDirection: 'column', 
                            justifyContent: 'center',
                            alignItems: 'center',
                            gap: '1rem', 
                          }}
                        >
                          <Button
                            size="large"
                            type="primary"
                            shape="round"
                            style={{
                              fontWeight: 500,
                            }}
                            icon={<PlusOutlined style={{ fontSize: 22, color: '#eee' }} />}
                            onClick={() => handleAddGroups(ActivityType.Group)}
                          >
                            {t('common.create-group')}
                          </Button>
                          <DeleteGroupActivities

                            groups={cardsGroups.map((card) => card.props.activity)}
                            activities={activities}
                            onDeleteGroupActivities={(activityId) => onRxColActivity(RxColOp.Remove, { id: activityId })}
                          />

                        </div>
                      </Col>

                    
                      <Col className='mix-main_content'>
                        <div className='mix-spacer' />
                        <div className='mix-spacer' />
                        <Flex wrap gap="middle" >
                          {cardsGroups}
                        </Flex>
                      </Col>

                    </Row>
                  </Content>,
                  icon: <FolderOutlined/>
                },
                {
                  key: 'path',
                  label: <span>{t('common.path-title')} <span className='mix-tab_qte'>{cardsPaths.length}</span></span>,
                  children:
                  <Content className='mix-appWrap'>
                    <Row className='mix-main'>
                      <Col className='mix-main_aside'>
                        <div className='mix-spacer' />
                        <PathDescription />
                        <Button
                          size='large'
                          type='primary'
                          shape='round'
                          css={{
                            fontWeight: 500,
                            margin: 'auto',
                          }}
                          icon={<PlusOutlined css={{ fontSize: 22, color: '#eee' }} />}
                          onClick={() => handleAddPath(ActivityType.Path)}>
                          {t('common.create-path')}
                        </Button>
                      </Col>
                      
                      <Col className='mix-main_content'>
                        <div className='mix-spacer' />
                        <div className='mix-spacer' />
                        <Flex wrap gap="middle" >
                        {cardsPaths}
                        </Flex>
                      </Col>

                    </Row>
                  </Content>,
                  icon: <FolderOutlined/>
                },
              ]
              }>
            
          </Tabs>
        </section>
      </section>
      
    </Layout>  
    // </Space>
  );
}
