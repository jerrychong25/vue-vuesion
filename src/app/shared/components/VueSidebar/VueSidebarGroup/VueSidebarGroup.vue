<template>
  <div :class="$style.vueSidebarGroup">
    <strong>{{ title }}</strong>
    <ul>
      <slot />
    </ul>
  </div>
</template>

<script lang="ts">
export default {
  name: "VueSidebarGroup",
  props: {
    title: {
      required: true,
      type: String,
    },
  },
};
</script>

<style lang="scss" module>
@import "../../../design-system";

.vueSidebarGroup {
  display: block;
  margin: $sidebar-group-margin;

  ul {
    padding: 0;
  }

  strong {
    display: block;
    font-weight: $font-weight-regular;
    font-size: $sidebar-group-font-size;
    border-bottom: $sidebar-group-border-bottom;
    padding-bottom: $space-4;
    margin-bottom: $space-12;
  }
}
</style>
