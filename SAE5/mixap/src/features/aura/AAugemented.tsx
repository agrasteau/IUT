import { a as a3f, useSpring } from '@react-spring/three';
import { animated } from '@react-spring/web';
import { Html } from '@react-three/drei';
import { useThree } from '@react-three/fiber';
import { useGesture } from '@use-gesture/react';
import { Resizable } from 're-resizable';
import { useCallback, useState } from 'react';

//import { animated } from '@react-spring/web';
//import { Html } from '@react-three/drei';
import { Popover, Typography } from 'antd';

import {
  CameraOutlined,
  DeleteOutlined,
  HolderOutlined,
  RotateRightOutlined,
  UploadOutlined
} from '@ant-design/icons';
import { PlayArrow } from '@mui/icons-material';
import SwipeIcon from '@mui/icons-material/Swipe';
import ThreeDRotationIcon from '@mui/icons-material/ThreeDRotation';
import { IconButton } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { Image3f } from '../../components';
import { TRACES } from '../../db/traces';
import { calcAspect } from '../../hooks/useAspect';
import useControls from '../../hooks/useControls';
import useLogger from '../../hooks/useLogger';
import useStore from '../../hooks/useStore';
import { useTrace } from '../../hooks/useTrace';
import { loadImageBase64 } from '../../utils/loadImage';
import { getType } from '../../utils/mimetype';
import { mxResizable } from '../../utils/styles';
import { AuraMode } from '../editor/Board';

export function AAugemented({ canvasRef, id, anchoring, mode, onChange, onDelete }: any) {
  const log = useLogger('AAugemented');
  //log.debug('Render');
  const { content, cfg, activityId, type } = useStore((store) =>
    store.auraSlice.find(id),
  ) as any;
  const { viewport, size } = useThree();
  const widthFactor = size.width / viewport.width;
  const heightFactor = size.height / viewport.height;
  const [switchGuesture, setSwitchGesture] = useState<any>('drag');
  const [dragging, setDragging] = useState<boolean>(false);
  const [visibleControls, setVisibleControls] = useState<boolean>(true);
  const { trace } = useTrace({});
  const { t } = useTranslation();
  const handleControlsVisibility = (visible) => {
    setVisibleControls(visible);
  };
  const handleGuesture = (type) => {
    setSwitchGesture(type);

    switch (type) {
      case 'drag': {
        api.start({
          position: [
            cfg.position[0] + 0.3,
            cfg.position[1] + 0.3,
            cfg.position[2],
          ],
        });
        setTimeout(() => {
          api.start({
            position: [cfg.position[0], cfg.position[1], cfg.position[2]],
          });
        }, 700);
        break;
      }
      case 'rotate2D': {
        api.start({
          rotation: [cfg.rotation[0], cfg.rotation[1], cfg.rotation[2] + 0.3],
        });
        setTimeout(() => {
          api.start({
            rotation: [cfg.rotation[0], cfg.rotation[1], cfg.rotation[2]],
          });
        }, 700);
        break;
      }
      case 'rotate3D': {
        api.start({
          rotation: [
            cfg.rotation[0] + 0.3,
            cfg.rotation[1] + 0.3,
            cfg.rotation[2] + 0.3,
          ],
        });
        setTimeout(() => {
          api.start({
            rotation: [cfg.rotation[0], cfg.rotation[1], cfg.rotation[2]],
          });
        }, 700);
        break;
      }
      default:
        break;
    }
  };
  const [mediaVisible, setMediaVisible] = useState<boolean>(false); 
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [states, setStates] = useState<boolean>(false);
  const [generatedImage, setGeneratedImage] = useState(null);
  const handleOk = async () => {
    if (!generatedImage) {
      return;
    }
    const image = await fetch(generatedImage as string);
    const blob = await image.blob();
    const { file, worldWidth, worldHeight } = await loadImageBase64(blob);

    // Calculate the new size with aspect ratio
    const { newWidth: width, newHeight: height } = calcAspect({
      width: worldWidth,
      height: worldHeight,
      maxWidth: 200,
      maxHeight: 200,
    });

    handleChange({
      content: {
        file,
      },
      cfg: {
        worldWidth,
        worldHeight,
        width,
        height,
      },
    });
    setIsModalVisible(false);
  };
  const options = [
    {
      component: 'InputFile',
      icon: <UploadOutlined />,
      label: 'fileUpload',
      key: 'file',
      size: 'large',
      description: '',
      tip: 'une image',
      accept: 'image/png, image/jpeg, image/jpg, image/gif',
    },
    {
      component: 'Snapshot',
      icon: <CameraOutlined />,
      label: 'fileUploadCapture',
      key: 'file',
      size: 'large',
      description: '',
      tip: 'une image',
      capture: true,
      accept: 'image/png, image/jpeg, image/jpg',
    },
    {
      component: 'GroupButton',
      icon: (
        <SwipeIcon
          css={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        />
      ),
      label: 'guesture',
      key: 'guesture',
      defaultValue: 'holder',
      size: 'large',
      onClick: handleGuesture,
      options: [
        {
          label: 'Drag',
          value: 'drag',
          icon: (
            <HolderOutlined
              css={{
                fontSize: '1.2rem',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            />
          ),
        },
        {
          label: 'Rotation 2D',
          value: 'rotate2D',
          icon: (
            <RotateRightOutlined
              css={{
                fontSize: '1.2rem',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            />
          ),
        },
        {
          label: 'Rotation 3D',
          value: 'rotate3D',
          icon: (
            <ThreeDRotationIcon
              css={{
                fontSize: '1.2rem',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            />
          ),
        },
      ],
      description: '',
    },
    {
      component: 'Button',
      icon: <DeleteOutlined />,
      label: 'delete',
      key: 'delete',
      defaultValue: 400,
      size: 'large',
      onClick: onDelete,
      description: '',
    },
  ];
  const { controlsForm } = useControls({
    options,
    fieldsValue: {},
    initialValues: {},
    onChange: ({ allFields }) => {
      if (allFields?.file) {
        (async () => {
          const { file, worldWidth, worldHeight } = await loadImageBase64(
            allFields.file,
          );

          const { newWidth: width, newHeight: height } = calcAspect({
            width: worldWidth,
            height: worldHeight,
            maxWidth: 200,
            maxHeight: 200,
          });

          handleChange({
            content: {
              file,
              // base64,
              type: getType(allFields.file?.name, type),
            },
            cfg: {
              worldWidth,
              worldHeight,
              width,
              height,
            },
          });
        })();
      }
    },
  });
  const [spring, api] = useSpring<any>(() => ({
    rotation: cfg?.rotation,
    position: cfg?.position,
  }));

  const handleChange = useCallback((payload) => {
      onChange &&
        onChange({
          id,
        activityId,
        type,
          ...payload,
        });
  }, []);
  const gesture = useGesture(
    {
      onDragEnd: ({ offset: [x, y] }) => {
        setDragging(false);
        setVisibleControls(true);

        if (switchGuesture === 'drag') {
          handleChange({
            cfg: {
              position: [x / widthFactor, -y / heightFactor, 0],
            },
          });
          trace(TRACES.DRAG_AURA);
        } else if (switchGuesture === 'rotate2D') {
          handleChange({
            cfg: {
              rotation: [
                cfg.rotation[0],
                cfg.rotation[1],
                (360 * x) / cfg.factor / 100,
              ],
            },
          });
        } else if (switchGuesture === 'rotate3D') {
          handleChange({
            cfg: {
              rotation: [y / 50, x / 50, cfg.rotation[2]],
            },
          });
        }
      },
      onDragStart: () => {
        setDragging(true);
        setVisibleControls(false);
      },
      onDrag: ({ pinching, cancel, offset: [x, y] }) => {
        if (pinching) {
          return cancel();
        }
        if (switchGuesture === 'drag') {
          api.start({
            position: [x / widthFactor, -y / heightFactor, 0],
          });
        } else if (switchGuesture === 'rotate2D') {
          api.start({
            rotation: [
              cfg.rotation[0],
              cfg.rotation[1],
              (360 * x) / cfg.factor / 100,
            ],
          });
        } else if (switchGuesture === 'rotate3D') {
          api.start({
            rotation: [y / 50, x / 50, cfg.rotation[2]],
          });
        }
      },
    },
    {
      // target: mesh,
      drag: { bounds: canvasRef, rubberband: true, pointer: { touch: true } },
    },
  );
  const button = {
    label: (
      <IconButton
        onClick={() => {
          setMediaVisible(!mediaVisible); // Toggle the media visibility
          console.log('Render clicked');
        }}
        sx={{
          backgroundColor: '#1976d2', // Blue color (can be customized)
          borderRadius: '50%', // Circle button
          padding: '12px', // Adjust the size of the circle
        }}
      >
        <PlayArrow sx={{ color: 'white' }} /> {/* Play icon for rendering */}
      </IconButton>
    ),
  };
  return (
    <>
      {mode === AuraMode.ARVIEW && (
        <group
          position={[
            (cfg.position[0] * cfg.factor + cfg.marker.width / 2) *
              cfg.marker.widthRatio,
            (cfg.position[1] * cfg.factor + cfg.marker.height / 2) *
              cfg.marker.heightRatio,
            0,
          ]}
          rotation={cfg.rotation}
          scale={[1, 1, 1]}>
          <Image3f
            position={[0, 0, 0]}
            scale={[1, 1, 1]}
            width={cfg.width * cfg.marker.widthRatio * cfg.factor}
            height={cfg.height * cfg.marker.heightRatio * cfg.factor}
            file={content.file}
            factor={cfg.factor}
            transparent
            opacity={0.95}
          />
        </group>
      )}

      {mode === AuraMode.ARCANVAS && (
        <group
          scale={[1, 1, 1]}
          position={cfg.position}
          rotation={cfg.rotation}>
          <Image3f
            file={content.file}
            transparent
            opacity={1}
            scale={[1, 1, 1]}
            width={cfg.width}
            factor={cfg.factor}
            height={cfg.height}
          />
        </group>
      )}

      {mode === AuraMode.CANVAS && (
        <a3f.group
          {...spring}
          scale={[1, 1, 1]}>
          {content.file && (
            <Image3f
              file={content.file}
              opacity={1}
              scale={[1, 1, 1]}
              width={cfg.width}
              height={cfg.height}
              factor={cfg.factor}
              transparent
            />
          )}

          <Html
            zIndexRange={[1, 1]}
            css={{
              left: -cfg.width / 2,
              top: -cfg.height / 2,
              cursor: switchGuesture === 'drag' ? 'move' : 'grab',
            }}>
            <Resizable //allow the media to be resized 
              css={mxResizable}
              size={{
                width: cfg.width,
                height: cfg.height,
              }}
              onResizeStop={(e, direction, ref, d) => {
                handleChange({
                  cfg: {
                    width: cfg.width + d.width,
                    height: cfg.height + d.height,
                    scale: [
                      (cfg.width + d.width) / cfg.factor,
                      (cfg.height + d.height) / cfg.factor,
                      1,
                    ],
                  },
                });
                trace(TRACES.RESIZE_AURA);
              }}>
              <Popover //blue popup up from the media allowing to upload, Snapshot, update and drop the media 
                color='var(--active-color)'
                content={controlsForm}
                visible={visibleControls}
                onVisibleChange={handleControlsVisibility}>
                <animated.div
                  {...gesture()}
                  css={{
                    width: '100%',
                    height: '100%',
                    touchAction: 'none',
                    display: 'flex',
                  }}>
                  {!content.file && (
                    <Typography.Title level={5}>
                      {t('common.click-edit')}
                    </Typography.Title>
                  )}
                </animated.div>
              </Popover>
            </Resizable>
          </Html>
        </a3f.group>
      )}
      {false && ( //temporary button used to display its self now controlled thanks to hide props
        <a3f.group position={cfg.position} rotation={cfg.rotation} scale={[1, 1, 1]}>
          <Html
            zIndexRange={[1, 1]}
            style={{
              position: 'absolute',
              left: -cfg.width / 2 + 25,
              bottom: -cfg.height / 2 + 25,  // Positioned at the bottom
              cursor: 'pointer',
              display: 'flex',
            }}
          >
            <div onClick={button.onClick}>{button.label}</div>
          </Html>
        </a3f.group>
      )}
    </>
  );
}
