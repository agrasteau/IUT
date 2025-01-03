export const getType = (mimetype: string, auraType: string) => {
  switch (auraType) {
    case 'AImage':
      return 'image';
    case 'AVideo':
      return 'video';
    case 'AAudio':
      return 'audio';
    case 'AAugemented':
      return 'image';
    case 'A3d':
      return mimetype.split('.').pop();
    default:
      break;
  }
};
